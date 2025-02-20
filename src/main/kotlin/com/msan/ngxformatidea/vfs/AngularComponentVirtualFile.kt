package com.msan.ngxformatidea.vfs

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.LightVirtualFile
import com.msan.ngxformatidea.language.NgxLanguage
import com.msan.ngxformatidea.utils.Logger
import java.nio.charset.StandardCharsets

val tsExtension = FileTypeManager.getInstance().getFileTypeByExtension("ts")
val ngxExtension = FileTypeManager.getInstance().getFileTypeByExtension("ngx")

class NgxVirtualFile(
    public val originalFiles: List<VirtualFile>,
    public val fileName: String,
    public val project: Project
//) : LightVirtualFile(fileName, ngxExtension, "") {
//) : LightVirtualFile(fileName, tsExtension, "") {
//) : LightVirtualFile(fileName, HtmlFileType.INSTANCE, "") {
//) : LightVirtualFile(fileName, HTMLLanguage.INSTANCE, "") {
//) : LightVirtualFile(fileName, PlainTextLanguage.INSTANCE, "") {
//) : LightVirtualFile(fileName, NgxLanguage, "") {
) : LightVirtualFile(fileName, NgxLanguage.INSTANCE, "") {

    public val ngxPsiFile: PsiFile? by lazy { PsiManager.getInstance(project).findFile(this) }

    init {
        isWritable = true
        updateContent()

//        charset = StandardCharsets.UTF_8
//
//        // Salviamo il riferimento all'originale
//        putUserData(ORIGINAL_FILE_KEY, this)
//
//        val original = getUserData(ORIGINAL_FILE_KEY)
//        if (original != null) {
//            println("NgxVirtualFile COPIA CREATA da ${original.name}")
//        } else {
//            println("NgxVirtualFile FILE ORIGINALE: $name")
//        }
//
//        ApplicationManager.getApplication().invokeLater {
//            updateContent()
//        }

    }

    override fun isDirectory(): Boolean = false


    fun updateContent() {
        Logger.warn("-------- Update content for ${fileName}")
        val tsFile = originalFiles.find { it.extension == "ts" }
        val htmlFile = originalFiles.find { it.extension == "html" }
        val styleFile = originalFiles.find { it.extension == "scss" } ?: originalFiles.find { it.extension == "css" }

        val tsContent = tsFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""
        val htmlContent = htmlFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""
        val styleContent = styleFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""

        val newContent = generateNgxContent(tsContent, htmlContent, styleContent)

        setContent(null, newContent, false)

//        setContent(null, newContent, false)
//        putUserData(ORIGINAL_FILE_KEY, this)

//        ApplicationManager.getApplication().invokeLater {
//            WriteCommandAction.runWriteCommandAction(project) {
//                setContent(null, newContent, false)
//            }
//        }
    }

//    companion object {
//        private val ORIGINAL_FILE_KEY = com.intellij.openapi.util.Key.create<VirtualFile>("ngx.original.file")
//    }

//    companion object {
//        val TEMPLATE_CONTEXT_KEY = com.intellij.openapi.util.Key.create<PsiFile>("ngx.template.context")
//        val STYLE_CONTEXT_KEY = com.intellij.openapi.util.Key.create<PsiFile>("ngx.style.context")
//        val COMPONENT_CONTEXT_KEY = com.intellij.openapi.util.Key.create<PsiFile>("ngx.component.context")
//    }

fun generateNgxContent(tsContent: String, htmlContent: String, styleContent: String): String {
return """
[template]
${htmlContent.trim()}
[/template]

[style]
${styleContent.trim()}
[/style]

[component]
${tsContent}
[/component]
"""
}
}