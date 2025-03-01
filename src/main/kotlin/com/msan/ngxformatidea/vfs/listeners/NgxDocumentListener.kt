package com.msan.ngxformatidea.vfs.listeners

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.msan.ngxformatidea.utils.Logger
import com.msan.ngxformatidea.vfs.NgxNode
import com.msan.ngxformatidea.vfs.NgxVirtualFile


class NgxDocumentListener(private val project: Project, private val ngxNode: NgxNode? = null) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        val document = event.document

//        Logger.warn("------ DOCUMENT changed: ${documentCurrent.text}")

        ApplicationManager.getApplication().invokeLater {
            FileDocumentManager.getInstance().getFile(document)?.let { file ->
//                if (file is NgxVirtualFile && !file.isUpdating) {
                if (file is NgxVirtualFile) {
                    Logger.warn("------ NGX FILE changed: evaluate ${file.name}")
                    file.updateOriginalFiles()
                }
                else{
//                    Logger.warn("------ RELATED FILE CHANGE: evaluate ${file}")
                    ApplicationManager.getApplication().runWriteAction {
                        // your code to modify file content
                        if(ngxNode != null) updateNgxFile(file, document.text, project, ngxNode)
                    }

                }
            }

        }
    }

    fun updateNgxFile(file: VirtualFile, content: String, project: Project, ngxNode: NgxNode) {
        try {
            val parentFile = ngxNode.virtualFile as NgxVirtualFile

            Logger.warn("------ RELATED FILE CHANGE: evaluate ${file}")
            Logger.warn("------ OLD CONTENT: ${content}")
            Logger.warn("------ CURRENT CONTENT: ${parentFile.content}")

            if (!parentFile.content.contains(content)) {
                Logger.warn("------ RELATED FILE UPDATING ${file}")
                parentFile.updateContent()
            }

        } catch (e: Exception) {
            Logger.warn("Error updating file content: ${e.message}")
        }
    }
}