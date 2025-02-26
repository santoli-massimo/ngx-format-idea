package com.msan.ngxformatidea.vfs.listeners

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.msan.ngxformatidea.utils.Logger
import com.msan.ngxformatidea.vfs.NgxVirtualFile


class NgxDocumentListener(private val project: Project) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        val document = event.document

//        Logger.warn("Document changed: ${document.text}")

        ApplicationManager.getApplication().invokeLater {

            FileDocumentManager.getInstance().getFile(document)?.let { file ->
                if (file is NgxVirtualFile && !file.isUpdating) {
                    Logger.warn("Document changed: ${file.name}")
                    file.updateOriginalFiles()
                }


            }

        }
    }
}