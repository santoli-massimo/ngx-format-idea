package com.msan.ngxformatidea

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.msan.ngxformatidea.vfs.NgxVirtualFile

class AngularComponentFileManager(private val project: Project) {
    private val virtualFiles = mutableMapOf<String, NgxVirtualFile>()

    fun getOrCreateVirtualFile(baseName: String, componentFiles: List<VirtualFile>): NgxVirtualFile {
        return virtualFiles.getOrPut(baseName) {
            val virtualFile = NgxVirtualFile(componentFiles, "$baseName.ngx", project)
            virtualFile.updateContent()
            virtualFile
        }
    }

    fun refreshFileSystem() {
        VirtualFileManager.getInstance().refreshWithoutFileWatcher(true)
    }
}