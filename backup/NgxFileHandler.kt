package com.msan.ngxformatidea.editor

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.msan.ngxformatidea.utils.Logger

class NgxFileHandler(private val project: Project) {

    fun loadNgxContent(ngxFile: VirtualFile): String {
        val tsFile = ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.ts"))
        val htmlFile = ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.html"))
        val styleFile = ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.scss"))
            ?: ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.css"))

        val tsContent = tsFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""
        val htmlContent = htmlFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""
        val styleContent = styleFile?.let { it.contentsToByteArray().toString(Charsets.UTF_8) } ?: ""

        Logger.warn("Tsfile ${tsContent}")
        Logger.warn("Htmlfile ${htmlContent}")
        Logger.warn("Stylefile ${styleContent}")


        return "pippo"

        return """
            <ng-component-template>
            $htmlContent
            </ng-component-template>

            <ng-component-style>
            $styleContent
            </ng-component-style>

            $tsContent
        """.trimIndent()
    }

    fun saveNgxContent(ngxFile: VirtualFile, document: com.intellij.openapi.editor.Document) {
        val content = document.text
        val tsFile = ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.ts"))
        val htmlFile = ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.html"))
        val styleFile = ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.scss"))
            ?: ngxFile.parent?.findChild(ngxFile.name.replace(".component.ngx", ".component.css"))

        // Extract sections from .component.ngx content
        val htmlContent = Regex("<ng-component-template>([\\s\\S]*?)</ng-component-template>").find(content)?.groupValues?.get(1)?.trim() ?: ""
        val styleContent = Regex("<ng-component-style>([\\s\\S]*?)</ng-component-style>").find(content)?.groupValues?.get(1)?.trim() ?: ""
        val tsContent = content.replace(Regex("<ng-component-template>[\\s\\S]*?</ng-component-template>"), "")
            .replace(Regex("<ng-component-style>[\\s\\S]*?</ng-component-style>"), "").trim()

        WriteCommandAction.runWriteCommandAction(project) {
            document.setText("pluto")
        }

        // Perform file updates inside a write action
//        WriteCommandAction.runWriteCommandAction(project) {
//            if (tsFile != null) {
//                tsFile.setBinaryContent(tsContent.toByteArray(Charsets.UTF_8))
//            }
//            if (htmlFile != null) {
//                htmlFile.setBinaryContent(htmlContent.toByteArray(Charsets.UTF_8))
//            }
//            if (styleFile != null) {
//                styleFile.setBinaryContent(styleContent.toByteArray(Charsets.UTF_8))
//            }
//        }

//        WriteCommandAction.runWriteCommandAction(project) {
//            document.setText(loadNgxContent(ngxFile))
//        }
    }
}
