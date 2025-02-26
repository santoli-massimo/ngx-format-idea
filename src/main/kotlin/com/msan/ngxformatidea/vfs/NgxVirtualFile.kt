package com.msan.ngxformatidea.vfs

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.testFramework.LightVirtualFile
import com.msan.ngxformatidea.language.NgxLanguage
import com.msan.ngxformatidea.utils.Logger


class NgxVirtualFile(
    public val originalFiles: List<VirtualFile>,
    public val fileName: String,
    public val project: Project
) : LightVirtualFile(fileName, NgxLanguage.INSTANCE, "") {
    public val ngxPsiFile: PsiFile? by lazy { PsiManager.getInstance(project).findFile(this) }
    public var isUpdating = false


    init {
        isWritable = true
        updateContent()
    }

    override fun isDirectory(): Boolean = false

    fun updateContent() {
        if (isUpdating) return
        isUpdating = true

        try {
            val (tsContent, htmlContent, styleContent) = run {
                var ts = ""
                var html = ""
                var style = ""
                for (file in originalFiles) {
                    when (file.extension) {
                        "ts"   -> ts = file.contentsToByteArray().toString(Charsets.UTF_8)
                        "html" -> html = file.contentsToByteArray().toString(Charsets.UTF_8)
                        "css", "scss" -> style = file.contentsToByteArray().toString(Charsets.UTF_8)
                    }
                    if (ts.isNotEmpty() && html.isNotEmpty() && style.isNotEmpty()) break
                }
                Triple(ts, html, style)
            }

            setContent(null, generateNgxContent(tsContent, htmlContent, styleContent), false)

            // ApplicationManager.getApplication().runWriteIntentReadAction<Unit, Throwable> { setContent(null, newContent, false) }
        } finally {
            isUpdating = false
        }
    }

    fun updateOriginalFiles() {
        Logger.warn("Updating original files: $isUpdating")
        if (isUpdating) return
        isUpdating = true

        try {
            val document = FileDocumentManager.getInstance().getDocument(this)
            val content = document?.text ?: ""

            val templateContent = extractContent(content, "[template]", "[/template]")
            val styleContent = extractContent(content, "[style]", "[/style]")
            val componentContent = extractContent(content, "[component]", "[/component]")

            ApplicationManager.getApplication().runWriteAction {
                originalFiles.forEach { file ->
                    when (file.extension) {
                        "html" -> updateFileContent(file, templateContent)
                        "css", "scss" -> updateFileContent(file, styleContent)
                        "ts" -> updateFileContent(file, componentContent)
                    }
                }
            }
        }
        catch (e: Exception) {
            Logger.warn("Error updating original files: ${e.message}")
        }
        finally { isUpdating = false }
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

    private fun updateFileContent(file: VirtualFile, content: String) {
        try {
            val document = FileDocumentManager.getInstance().getDocument(file)
            if (document != null) {
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
            |${htmlContent.trim().prependIndent("\t")}
            |[/template]
            |
            |[style]
            |${styleContent.trim().prependIndent("\t")}
            |[/style]
            |
            |[component]
            |${tsContent.trim().prependIndent("\t")}
            |[/component]
        """.trimMargin()
    }

}