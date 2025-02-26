package com.msan.ngxformatidea.vfs.listeners

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project
import com.msan.ngxformatidea.utils.Logger

class OriginalFileListener(private val project: Project) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        // This will be executed every time the document is modified.
        val document = event.document
        // You can add your custom logic here.
        Logger.warn("ORIGINAL Document changed: ${document.text}")
    }
}