package com.msan.ngxformatidea.editor

import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.editor.Document
import org.jdom.Element


class NgxFileEditorProvider : FileEditorProvider {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        return file.extension == "ngx"
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        val ngxHandler = NgxFileHandler(project)
        val documentManager = FileDocumentManager.getInstance()

        // Check if a document exists for the file, otherwise create a new one
        val document = documentManager.getDocument(file) ?: createNgxDocument(project, file, ngxHandler)

        return TextEditorProvider.getInstance().createEditor(project, file)
    }

    private fun createNgxDocument(project: Project, file: VirtualFile, ngxHandler: NgxFileHandler): Document {
        val documentManager = FileDocumentManager.getInstance()
        val document = documentManager.getDocument(file)

        if (document != null) {
            document.setText(ngxHandler.loadNgxContent(file)) // Load the generated content

            // Listen for document changes and save them
            document.addDocumentListener(object : com.intellij.openapi.editor.event.DocumentListener {
                override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent) {
                    ngxHandler.saveNgxContent(file, document)
                }
            })
        }

        return document ?: throw IllegalStateException("Could not create document for ${file.path}")
    }

    override fun getEditorTypeId(): String = "NgxFileEditor"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR

    override fun readState(element: Element, project: Project, file: VirtualFile): FileEditorState {
        return FileEditorState.INSTANCE
    }

    override fun writeState(state: FileEditorState, project: Project, element: Element) {}
}
