package com.msan.ngxformatidea.vfs

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.LightVirtualFile
import com.msan.ngxformatidea.language.NgxLanguage
import com.msan.ngxformatidea.psi.impl.NgxComponentMixin
import com.msan.ngxformatidea.psi.impl.NgxStyleMixin
import com.msan.ngxformatidea.psi.impl.NgxTemplateMixin
import com.msan.ngxformatidea.psi.impl.base.NgxPsiInjectionHostMixin
import com.msan.ngxformatidea.utils.Logger
import com.msan.ngxformatidea.vfs.listeners.NgxChildDocumentListener
import com.msan.ngxformatidea.vfs.listeners.NgxDocumentListener


class NgxVirtualFile(
    public val originalFiles: List<VirtualFile>,
    public val fileName: String,
    public val project: Project
) : LightVirtualFile(fileName, NgxLanguage.INSTANCE, ""){
//    public val ngxPsiFile: PsiFile? by lazy { PsiManager.getInstance(project).findFile(this) }
    private var editorListenersDisposable: Disposable = Disposer.newDisposable("NgxFileCompositeDisposable-$fileName")

    fun onEditorClosed(file: VirtualFile) {
        val openEditors = FileEditorManager.getInstance(project).getEditors(file)
        if(openEditors.isEmpty()){
            Logger.warn("------ DISPOSE ALL LISTENERS for: ${file.name}")
            Disposer.dispose(editorListenersDisposable)
            editorListenersDisposable = Disposer.newDisposable("NgxFileCompositeDisposable-$fileName")
        }
    }

    init {
        isWritable = true
        initContent()
    }

    private fun addSyncListeners(){
        ApplicationManager.getApplication().invokeLater({
            project.messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, object : FileEditorManagerListener {
                override fun fileOpened(source: FileEditorManager, file: VirtualFile) {
                    if(file !is NgxVirtualFile) return
                    val document = FileDocumentManager.getInstance().getDocument(file) ?: return

                    document.addDocumentListener(NgxDocumentListener(file, project), editorListenersDisposable)

                    file.originalFiles.forEach{ child ->
                        val childDocument = FileDocumentManager.getInstance().getDocument(child) ?: return
                        childDocument.addDocumentListener(NgxChildDocumentListener(file, child, project), editorListenersDisposable)
                    }
                }

                override fun fileClosed(source: FileEditorManager, file: VirtualFile) {
                    if(file !is NgxVirtualFile) return
                    onEditorClosed(file)
                }
            })
        }, ModalityState.nonModal())
    }

    override fun isDirectory(): Boolean = false
    private fun initContent() {
        try {
            val (tsContent, htmlContent, styleContent) = run {
                var ts = ""
                var html = ""
                var style = ""
                for (file in originalFiles) {
                    val document = FileDocumentManager.getInstance().getDocument(file) ?: return
                    when (file.extension) {
                        "ts" -> ts = document.text.trimEnd()
                        "html" -> html = document.text.trimEnd()
                        "css", "scss" -> style = document.text.trimEnd()
                    }
                }
                Triple(ts, html, style)
            }
            setContent(null, generateNgxContent(tsContent, htmlContent, styleContent), false)

            addSyncListeners()
        }
        catch (e: Exception) {
            Logger.warn("Error updating content: ${e.message}")
        }
    }

    fun syncChildToNgx(childFile: VirtualFile) {

        val ngxDocument = FileDocumentManager.getInstance().getDocument(this) ?: return
        val childDocument = FileDocumentManager.getInstance().getDocument(childFile) ?: return
        val ngxPsiFile = PsiManager.getInstance(project).findFile(this) ?: return

        val (ngxPsiElement: NgxPsiInjectionHostMixin?, ngxPsiContent: String) = when (childFile.extension) {
            "html" -> {
                PsiDocumentManager.getInstance(project).commitDocument(ngxDocument)
                val psiElement = PsiTreeUtil.findChildOfType(ngxPsiFile, NgxTemplateMixin::class.java) as NgxTemplateMixin
                Pair(psiElement, psiElement.content())
            }
            "css", "scss" -> {
                PsiDocumentManager.getInstance(project).commitDocument(ngxDocument)
                val psiElement = PsiTreeUtil.findChildOfType(ngxPsiFile, NgxStyleMixin::class.java) as NgxStyleMixin
                Pair(psiElement, psiElement.content())
            }
            "ts" -> {
                PsiDocumentManager.getInstance(project).commitDocument(ngxDocument)
                val psiElement = PsiTreeUtil.findChildOfType(ngxPsiFile, NgxComponentMixin::class.java) as NgxComponentMixin
                Pair(psiElement, psiElement.content())
            }
            else -> Pair(null, "")
        }

        if(ngxPsiElement == null) return

        // Use of trimEnd() is trimendously important:
        // if IDE adds extra new lines at the end of the file, to avoid looping in the sync process.
        if(childDocument.text.trimEnd() != ngxPsiContent.trimEnd()) {
            Logger.warn("------ syncChildToNgx:")
            Logger.warn("Ngx text: ${System.lineSeparator()}${ngxPsiContent}");
            Logger.warn("Child text: ${System.lineSeparator()}${childDocument.text}");
            Logger.warn("----------------------");

            WriteCommandAction.runWriteCommandAction(project){
                ngxPsiElement.updateContent(childDocument.text.trimEnd())
                PsiDocumentManager.getInstance(project).commitDocument(ngxDocument)
            }

//            val childPsiFile = PsiManager.getInstance(project).findFile(childFile) ?: return
//            WriteCommandAction.runWriteCommandAction(project, "OnChildFileChanged${childFile.name}", "NgxSyncUp", {
//                ngxPsiElement.updateContent(childDocument.text.trimEnd())
//                PsiDocumentManager.getInstance(project).commitDocument(ngxDocument)
//
////                FileDocumentManager.getInstance().saveDocument(ngxDocument)
////                this.refresh(true, false)
//            }, ngxPsiFile, childPsiFile)
        }

    }

    fun syncNgxToChild() {
        try {
            val ngxDocument = FileDocumentManager.getInstance().getDocument(this) ?: return
            val ngxPsiFile = PsiManager.getInstance(project).findFile(this) ?: return

            PsiDocumentManager.getInstance(project).commitDocument(ngxDocument)

            originalFiles.forEach { file: VirtualFile ->
                when (file.extension) {
                    "html" -> {
                        val psiElement =
                            PsiTreeUtil.findChildOfType(ngxPsiFile, NgxTemplateMixin::class.java) as NgxTemplateMixin
                                ?: return@forEach
                        updateChild(file, psiElement.content())
                    }

                    "css", "scss" -> {
                        val psiElement =
                            PsiTreeUtil.findChildOfType(ngxPsiFile, NgxStyleMixin::class.java) as NgxStyleMixin
                                ?: return@forEach
                        updateChild(file, psiElement.content())
                    }

                    "ts" -> {
                        val psiElement =
                            PsiTreeUtil.findChildOfType(ngxPsiFile, NgxComponentMixin::class.java) as NgxComponentMixin
                                ?: return@forEach
                        updateChild(file, psiElement.content())
                    }
                }
            }
        }
        catch (e: Exception) { Logger.warn("Error updating original files: ${e.message}") }
    }

    private fun updateChild(childVirtualFile: VirtualFile, ngxPsiElementContent: String) {
        try {
            val childDocument = FileDocumentManager.getInstance().getDocument(childVirtualFile) ?: return

            if (childDocument.text.trimEnd() != ngxPsiElementContent.trimEnd()) {
                Logger.warn("++++++ syncNgxToChild")
                Logger.warn("NgX text: ${System.lineSeparator()}${ngxPsiElementContent}");
                Logger.warn("Child text: ${System.lineSeparator()}${childDocument.text}");
                Logger.warn("++++++++++++++++++++");

                WriteCommandAction.runWriteCommandAction(project){
                    childDocument.setText(ngxPsiElementContent)
                    PsiDocumentManager.getInstance(project).commitDocument(childDocument)
                }

//                val psiFile = PsiManager.getInstance(project).findFile(this) ?: return
//                val psiChildFile = PsiManager.getInstance(project).findFile(childVirtualFile) ?: return
//                WriteCommandAction.runWriteCommandAction(project, "UpdateOriginalFiles${childVirtualFile.name}", "NgxSyncDown", {
//                    childDocument.setText(ngxPsiElementContent)
//                    PsiDocumentManager.getInstance(project).commitDocument(childDocument)
//
//                    // Following lines are NOT strictly required, but can be useful for other ide parts or third-party plugins
////                    FileDocumentManager.getInstance().saveDocument(document)
////                    file.refresh(true,false)
//                }, psiFile, psiChildFile)
            }
        } catch (e: Exception) {
            Logger.warn("Error updating file content: ${e.message}")
        }
    }

    private fun generateNgxContent(tsContent: String, htmlContent: String, styleContent: String): String {
        // Those pipes char at the beginning of each line are important for trimMargin to work properly and maintain
        // the correct indentation of the original code
        // because the code indentation in this function affect the final result
        return """
            |[template]
            |${htmlContent}
            |[/template]
            |
            |[style]
            |${styleContent}
            |[/style]
            |
            |[component]
            |${tsContent}
            |[/component]
        """.trimMargin()
//        return """
//            |[template]
//            |${htmlContent.trim().prependIndent("\t")}
//            |[/template]
//            |
//            |[style]
//            |${styleContent.trim().prependIndent("\t")}
//            |[/style]
//            |
//            |[component]
//            |${tsContent.trim().prependIndent("\t")}
//            |[/component]
//        """.trimMargin()
    }
}