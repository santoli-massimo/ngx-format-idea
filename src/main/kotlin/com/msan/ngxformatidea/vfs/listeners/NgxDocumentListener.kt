package com.msan.ngxformatidea.vfs.listeners

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Computable
import com.intellij.openapi.util.RecursionManager
import com.intellij.openapi.vfs.VirtualFile
import com.msan.ngxformatidea.vfs.NgxVirtualFile
import com.msan.ngxformatidea.utils.Logger

const val updateKey: String = "ngxDocumentListener"

class NgxDocumentListener(
    private val ngxFile: NgxVirtualFile,
    private val project: Project
) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        ApplicationManager.getApplication().invokeLater{
            ngxFile.syncNgxToChild()
        }
    }
}

class NgxChildDocumentListener(
    private val ngxFile: NgxVirtualFile,
    private val childFile: VirtualFile,
    private val project: Project
) : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        ApplicationManager.getApplication().invokeLater{
            ngxFile.syncChildToNgx(childFile)
        }
    }
}
