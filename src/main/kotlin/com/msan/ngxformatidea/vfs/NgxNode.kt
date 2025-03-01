package com.msan.ngxformatidea.vfs

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.pom.Navigatable
import com.msan.ngxformatidea.utils.Logger
import com.msan.ngxformatidea.vfs.listeners.NgxDocumentListener


fun openEditor(project: Project, virtualFile: VirtualFile, ngxNode: NgxNode? = null) {
    val fileEditors = FileEditorManager.getInstance(project).openFile(virtualFile, true)
    fileEditors.forEach { fileEditor: FileEditor ->
        val textEditor = fileEditor as? TextEditor ?: return@forEach
        val editor = textEditor.editor
//        Logger.warn("editor")
//        Logger.warn(editor.document.text)

        editor.document.addDocumentListener(NgxDocumentListener(project, ngxNode), fileEditor)
    }
}

class NgxNode(
    project: Project?,
//    private val virtualFile: NgxVirtualFile,
    private val virtualFile: VirtualFile,
    private val relatedFiles: List<VirtualFile>,
    viewSettings: ViewSettings,
) : AbstractTreeNode<VirtualFile>(project, virtualFile), Navigatable {

    override fun getChildren(): Collection<AbstractTreeNode<*>> {
        return relatedFiles.map { file -> NgxChildNode(project, file, this) }
    }

    override fun update(presentation: PresentationData) {
        presentation.presentableText = virtualFile.name

        val fileType = FileTypeManager.getInstance().getFileTypeByFile(virtualFile)
        presentation.setIcon(fileType.icon)
        presentation.presentableText = virtualFile.name
    }

    override fun canNavigate(): Boolean = true
    override fun canNavigateToSource(): Boolean = true

    override fun navigate(requestFocus: Boolean) {
        openEditor(project, virtualFile)
    }

    public override fun getVirtualFile(): VirtualFile {
        return virtualFile
    }
}


class NgxChildNode(
    project: Project?,
    private val virtualFile: VirtualFile,
    private val ngxNode: NgxNode
): AbstractTreeNode<VirtualFile>(project, virtualFile), Navigatable {
    override fun getChildren(): Collection<AbstractTreeNode<*>> = emptyList()

    override fun update(presentation: PresentationData) {
//        presentation.presentableText = virtualFile.name

        val fileType = FileTypeManager.getInstance().getFileTypeByFile(virtualFile)
        presentation.setIcon(fileType.icon)
        presentation.presentableText = virtualFile.name
    }
    override fun canNavigate(): Boolean = true
    override fun canNavigateToSource(): Boolean = true

    override fun navigate(requestFocus: Boolean) {
//        val fileEditors = FileEditorManager.getInstance(project).openFile(virtualFile, true)
        openEditor(project, virtualFile, ngxNode)
    }
}
