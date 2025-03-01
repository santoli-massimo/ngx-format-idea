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
        updateContent(true)
        Logger.warn("CREATE NGX VIRTUAL FILE: $fileName")
    }

    override fun isDirectory(): Boolean = false

//    fun updateContent(template: String? = null, style: String? = null, component: String? = null) {
    fun updateContent(init: Boolean? = false) {
        try {
            val (tsContent, htmlContent, styleContent) = run {
                var ts = ""
                var html = ""
                var style = ""
                for (file in originalFiles) {
                    val document = FileDocumentManager.getInstance().getDocument(file) ?: continue
                    when (file.extension) {
                        "ts"   -> ts = document.text
                        "html" -> html = document.text
                        "css", "scss" -> style = document.text
                    }
                }
                Triple(ts, html, style)
            }
            if(init == true) {
                setContent(null, generateNgxContent(tsContent, htmlContent, styleContent), false)
            }
            else updateFileContent(this, generateNgxContent(tsContent, htmlContent, styleContent), project)

        }
        catch (e: Exception) {
            Logger.warn("Error updating content: ${e.message}")
        }
    }

    fun updateOriginalFiles() {
        try {
            val document = FileDocumentManager.getInstance().getDocument(this)
            val content = document?.text ?: ""

            val templateContent = extractContent(content, "[template]", "[/template]")
            val styleContent = extractContent(content, "[style]", "[/style]")
            val componentContent = extractContent(content, "[component]", "[/component]")

            ApplicationManager.getApplication().runWriteAction {
                originalFiles.forEach { file ->
                    when (file.extension) {
                        "html" -> updateFileContent(file, templateContent, project)
                        "css", "scss" -> updateFileContent(file, styleContent, project)
                        "ts" -> updateFileContent(file, componentContent, project)
                    }
                }
            }
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