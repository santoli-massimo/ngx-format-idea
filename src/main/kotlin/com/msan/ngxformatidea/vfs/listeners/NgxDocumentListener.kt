package com.msan.ngxformatidea.vfs.listeners

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.msan.ngxformatidea.psi.impl.NgxTemplateImpl
import com.msan.ngxformatidea.utils.Logger
import com.msan.ngxformatidea.vfs.NgxNode
import com.msan.ngxformatidea.vfs.NgxVirtualFile


class NgxDocumentListener(private val ngxFile: NgxVirtualFile) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        ngxFile.updateOriginalFiles()
    }
}

class NgxChildDocumentListener(
    private val ngxFile: NgxVirtualFile,
    private val childFile: VirtualFile
) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        ApplicationManager.getApplication().invokeLater({
            ngxFile.onChildFileChanged(childFile)
        })
    }

    fun removeMarkers(content: String, startMarker: String, endMarker: String): String {
        return content.replace(startMarker, "").replace(endMarker, "").trim()
    }
}
