package com.msan.ngxformatidea.vfs

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.msan.ngxformatidea.utils.Logger

class AngularComponentFileManager(private val project: Project) {
    private val virtualFiles = mutableMapOf<String, NgxVirtualFile>()

    fun getOrCreateVirtualFile(baseName: String, componentFiles: List<VirtualFile>): NgxVirtualFile {
        return virtualFiles.getOrPut(baseName) {
            val file = virtualFiles[baseName] ?: NgxVirtualFile(componentFiles, "$baseName.ngx", project)
            file.updateContent()
            file
        }
    }

    fun refreshFileSystem() {
        VirtualFileManager.getInstance().refreshWithoutFileWatcher(true)
    }
}