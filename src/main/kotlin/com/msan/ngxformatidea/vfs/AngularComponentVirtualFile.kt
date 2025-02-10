package com.msan.ngxformatidea.vfs

import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.LightVirtualFile
import com.msan.ngxformatidea.language.NgxFileType
import com.msan.ngxformatidea.language.NgxLanguage

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
) : LightVirtualFile(fileName, NgxFileType.INSTANCE, "") {

    public val ngxPsiFile: PsiFile? by lazy { PsiManager.getInstance(project).findFile(this) }
    init {
        isWritable = true
        updateContent()
    }

    override fun isDirectory(): Boolean = false

    fun updateContent() {
        val tsFile = originalFiles.find { it.extension == "ts" }
        val htmlFile = originalFiles.find { it.extension == "html" }
        val styleFile = originalFiles.find { it.extension == "scss" } ?: originalFiles.find { it.extension == "css" }

        val tsContent = tsFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""
        val htmlContent = htmlFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""
        val styleContent = styleFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""

        val newContent = generateNgxContent(tsContent, htmlContent, styleContent)

        setContent(null, newContent, false)
    }


fun generateNgxContent(tsContent: String, htmlContent: String, styleContent: String): String {
return """
[component]
<ng-component-template>
${htmlContent.trim()}
</ng-component-template>
[/component]

<ng-component-style>
${styleContent.trim()}
</ng-component-style>

${tsContent}
"""
}
}