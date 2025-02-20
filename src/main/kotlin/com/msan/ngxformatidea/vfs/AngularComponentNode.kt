package com.msan.ngxformatidea.vfs

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.pom.Navigatable


class AngularComponentNode(
    project: Project?,
    private val virtualFile: NgxVirtualFile,
    private val relatedFiles: List<VirtualFile>,
    viewSettings: ViewSettings,
//) : ProjectViewNode<VirtualFile>(project, virtualFile, viewSettings) {
) : AbstractTreeNode<VirtualFile>(project, virtualFile) {
    private val logger = Logger.getInstance(AngularTreeStructureProvider::class.java)

    override fun getChildren(): Collection<AbstractTreeNode<*>> {
        return relatedFiles.map { file -> AngularComponentChildNode(project, file) }
    }

    override fun update(presentation: PresentationData) {
        presentation.presentableText = virtualFile.name

        val fileType = FileTypeManager.getInstance().getFileTypeByFile(virtualFile)
        presentation.setIcon(fileType.icon)
        presentation.presentableText = virtualFile.name
    }

//
//    override fun contains(file: VirtualFile): Boolean {
//        TODO("Not yet implemented")
//    }
//
    override fun canNavigate(): Boolean = true
    override fun canNavigateToSource(): Boolean = true

//    override fun canRepresent(element: Any?): Boolean {
//        return element == virtualFile
//    }

    override fun navigate(requestFocus: Boolean) {
        logger.warn("OPEN NG FILE to ${virtualFile.name}")
        project?.let { FileEditorManager.getInstance(it).openFile(virtualFile, requestFocus) }
    }
}


class AngularComponentChildNode(project: Project?, private val file: VirtualFile): AbstractTreeNode<VirtualFile>(project, file), Navigatable
{
    private val logger = Logger.getInstance(AngularTreeStructureProvider::class.java)

    override fun getChildren(): Collection<AbstractTreeNode<*>> = emptyList()

    override fun update(presentation: PresentationData) {
        presentation.presentableText = file.name

        val fileType = FileTypeManager.getInstance().getFileTypeByFile(file)
        presentation.setIcon(fileType.icon)
        presentation.presentableText = file.name
    }

    override fun canNavigate(): Boolean = true
    override fun canNavigateToSource(): Boolean = true

    override fun navigate(requestFocus: Boolean) {
        project?.let { FileEditorManager.getInstance(it).openFile(file, requestFocus) }
    }
}
