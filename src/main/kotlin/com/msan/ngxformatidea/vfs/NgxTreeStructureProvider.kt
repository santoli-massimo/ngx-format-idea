package com.msan.ngxformatidea.vfs

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile


/*
* Creates a tree structure for the ngx files grouping the component files under the same virtual node (ngx file)
* */
class NgxTreeStructureProvider(private val project: Project) : TreeStructureProvider {
//    private val fileManager = AngularComponentFileManager(project)
    private val fileManager by lazy { NgxFileManager.getInstance(project) }

    override fun modify(
        parent: AbstractTreeNode<*>,
        children: Collection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): Collection<AbstractTreeNode<*>> {
        val newChildren = mutableListOf<AbstractTreeNode<*>>()
        val groupedFiles = mutableMapOf<String, MutableList<VirtualFile>>()

        // @TODO: @PERFORMANCE:
        // Check if is more performant to:
        // Avoid iterating 2 times by creating the NgxNode here and append files to it one by one
        // or is a problem to because of recurrent mutation of groupedFiles or modification of the tree
        for (child in children) {
            val virtualFile = (child.value as? PsiFile)?.virtualFile

            if(virtualFile != null && virtualFile.name.contains("component.") && !virtualFile.name.contains(".ngx")) {
                val baseName = virtualFile.nameWithoutExtension.replace(".spec", "")
                groupedFiles.computeIfAbsent(baseName) { mutableListOf() }.add(virtualFile)
                continue
            }
            newChildren.add(child)
        }

        val ngxNodes = groupedFiles.map { (baseName, files) ->
            val virtualFile = fileManager.getOrCreateVirtualFile(baseName, files)
            NgxNode(parent.project, virtualFile, files, settings ?: ViewSettings.DEFAULT)
        }

        return newChildren.apply { addAll(ngxNodes) }
    }
}
