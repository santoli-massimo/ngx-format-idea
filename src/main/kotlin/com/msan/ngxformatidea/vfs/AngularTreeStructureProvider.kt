package com.msan.ngxformatidea.vfs

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.lang.Language
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.msan.ngxformatidea.utils.Logger


class AngularTreeStructureProvider(private val project: Project) : TreeStructureProvider {
    private val fileManager = AngularComponentFileManager(project)

    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): Collection<AbstractTreeNode<*>> {

        val newChildren = mutableListOf<AbstractTreeNode<*>>()
        val groupedFiles = mutableMapOf<String, MutableList<VirtualFile>>()

        for (child in children) {
            val virtualFile = (child.value as? PsiFile)?.virtualFile
            if(virtualFile != null && virtualFile.name.contains("component.") && !virtualFile.name.contains(".ngx")) {
                val baseName = virtualFile.nameWithoutExtension.replace(".spec", "")
                groupedFiles.computeIfAbsent(baseName) { mutableListOf() }.add(virtualFile)
                continue
            }
            newChildren.add(child)
        }

        for ((baseName, files) in groupedFiles) {
            val virtualFile = AngularComponentFileManager(project).getOrCreateVirtualFile(baseName, files)

            newChildren.add(
                AngularComponentNode(
                    parent.project,
                    virtualFile,
                    files,
                    settings ?: ViewSettings.DEFAULT
                )
            )
        }

        return newChildren
    }
}
