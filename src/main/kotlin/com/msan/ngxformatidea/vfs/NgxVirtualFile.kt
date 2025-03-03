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

    public val ngxPsiFile: PsiFile? by lazy { PsiManager.getInstance(project).findFile(this) }
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
                    when (file.extension) {
                        "ts"   -> ts = file.contentsToByteArray().toString(Charsets.UTF_8)
                        "html" -> html = file.contentsToByteArray().toString(Charsets.UTF_8)
                        "css", "scss" -> file.contentsToByteArray().toString(Charsets.UTF_8)
                    }
                }
                Triple(ts, html, style)
            }
            setContent(null, generateNgxContent(tsContent, htmlContent, styleContent), false)
        }
        catch (e: Exception) {
            Logger.warn("Error updating content: ${e.message}")
        }
    }

    fun onChildFileChanged(childFile: VirtualFile) {
        val childDocument = FileDocumentManager.getInstance().getDocument(childFile) ?: return
        val psiFile = PsiManager.getInstance(project).findFile(this) ?: return

        val (psiElement: NgxPsiInjectionHostMixin?, contentWithoutTags: String) = when (childFile.extension) {
            "html" -> {
                val psiElement = PsiTreeUtil.findChildOfType(psiFile, NgxTemplateMixin::class.java) as NgxTemplateMixin
                val content = psiElement.contentWithoutMarkers()
                Pair(psiElement, content)
            }
            "css", "scss" -> {
                val psiElement = PsiTreeUtil.findChildOfType(psiFile, NgxStyleMixin::class.java) as NgxStyleMixin
                val content = psiElement.contentWithoutMarkers()
                Pair(psiElement, content)
            }
            "ts" -> {
                val psiElement = PsiTreeUtil.findChildOfType(psiFile, NgxComponentMixin::class.java) as NgxComponentMixin
                val content = psiElement.contentWithoutMarkers()
                Pair(psiElement, content)
            }
            else -> Pair(null, "")
        }

        Logger.warn("------ Child Text: ${childDocument.text}")
        Logger.warn("------ PSI Text: ${contentWithoutTags}")
        Logger.warn("------ Should Change: ${childDocument.text != contentWithoutTags}")

        // @TODO: indentation makes the comparison fail
        if(childDocument.text != contentWithoutTags && psiElement != null) {
            val document = FileDocumentManager.getInstance().getDocument(this) ?: return

            WriteCommandAction.runWriteCommandAction(project) {
                PsiDocumentManager.getInstance(project).commitDocument(document)
                psiElement.updateContent(childDocument.text)

                FileDocumentManager.getInstance().saveDocument(document)
                this.refresh(true, false)
            }

            Logger.warn("------ CHANGing: ${childDocument.text !== content}")
        }

    }

    fun updateOriginalFiles() {
        try {
            val document = FileDocumentManager.getInstance().getDocument(this) ?: return
            val psiFile = PsiManager.getInstance(project).findFile(this) ?: return

            WriteCommandAction.runWriteCommandAction(project) {
                PsiDocumentManager.getInstance(project).commitDocument(document)

                originalFiles.forEach { file ->
                    when (file.extension) {
                        "html" -> {
                            val psiElement = PsiTreeUtil.findChildOfType(psiFile, NgxTemplateMixin::class.java) as NgxTemplateMixin ?: return@forEach
                            updateFileContent(file, psiElement.contentWithoutMarkers(), project)
                        }
                        "css", "scss" -> {
                            val psiElement = PsiTreeUtil.findChildOfType(psiFile, NgxStyleMixin::class.java) as NgxStyleMixin ?: return@forEach
                            updateFileContent(file, psiElement.contentWithoutMarkers(), project)
                        }
                        "ts" -> {
                            val psiElement = PsiTreeUtil.findChildOfType(psiFile, NgxComponentMixin::class.java) as NgxComponentMixin ?: return@forEach
                            updateFileContent(file, psiElement.contentWithoutMarkers(), project)
                        }
                    }
                }
            }

//            val document = FileDocumentManager.getInstance().getDocument(this)
//            val content = document?.text ?: ""
//
//            val templateContent = extractContent(content, "[template]", "[/template]")
//            val styleContent = extractContent(content, "[style]", "[/style]")
//            val componentContent = extractContent(content, "[component]", "[/component]")
//
//            ApplicationManager.getApplication().runWriteAction {
//                originalFiles.forEach { file ->
//                    when (file.extension) {
//                        "html" -> updateFileContent(file, templateContent, project)
//                        "css", "scss" -> updateFileContent(file, styleContent, project)
//                        "ts" -> updateFileContent(file, componentContent, project)
//                    }
//                }
//            }
        }
        catch (e: Exception) {
            Logger.warn("Error updating original files: ${e.message}")
        }
    }


    private fun extractContent(content: String, startTag: String, endTag: String): String {
        val startIdx = content.indexOf(startTag) + startTag.length
        val endIdx = content.indexOf(endTag)
        if (startIdx >= 0 && endIdx >= 0 && startIdx < endIdx) {
            return content.substring(startIdx, endIdx).trim().lines()
                .joinToString("\n") { it.removePrefix("\t") }
        }
        return ""
    }

    fun updateFileContent(file: VirtualFile, content: String, project: Project) {
        try {
            val document = FileDocumentManager.getInstance().getDocument(file) ?: return
            if (document.text != content) {
                document.setText(content)
                PsiDocumentManager.getInstance(project).commitDocument(document)

                // Following lines are NOT strictly required, but can be useful for other ide parts or third-party plugins
                FileDocumentManager.getInstance().saveDocument(document)
                file.refresh(true, false)
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
            |${htmlContent.trim()}
            |[/template]
            |
            |[style]
            |${styleContent.trim()}
            |[/style]
            |
            |[component]
            |${tsContent.trim()}
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



//        EditorFactory.getInstance().addEditorFactoryListener(object : EditorFactoryListener {
//            override fun editorCreated(event: EditorFactoryEvent) {
//                val editor = event.editor
//                val file = FileDocumentManager.getInstance().getFile(editor.document)
//
//                if(file !is NgxVirtualFile) return
//                Logger.warn("------ NGX FILE editor created: ${file.name}")
//                editor.document.addDocumentListener(NgxDocumentListener(project), editorListenersDisposable)
//
//                file.originalFiles.forEach{ child ->
//                    Logger.warn("------ NGX CHILD FILE editor created: ${child.name}")
//                    val childDocument = FileDocumentManager.getInstance().getDocument(child) ?: return
//                    childDocument.addDocumentListener(NgxDocumentListener(project), editorListenersDisposable)
//                }
//
//            }
//            override fun editorReleased(event: EditorFactoryEvent) {
//                val file = FileDocumentManager.getInstance().getFile(event.editor.document)
//                if(file !is NgxVirtualFile) return
//                Logger.warn("------ NGX FILE editor released: ${file.name}")
//                onEditorClosed(file)
//            }
//        }, project)