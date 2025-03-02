package com.msan.ngxformatidea.vfs

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile


@Service(Service.Level.PROJECT)
class NgxFileManager(private val project: Project) {
    private val ngxFiles = mutableMapOf<String, NgxVirtualFile>()

    companion object {
        fun getInstance(project: Project): NgxFileManager { return project.getService(NgxFileManager::class.java) }
    }

    fun getOrCreateVirtualFile(baseName: String, files: List<VirtualFile>): VirtualFile {
        return ngxFiles.getOrPut(baseName) {
            NgxVirtualFile(files, "$baseName.ngx", project)
        }
    }

    fun findAssociatedVirtualFile(file: VirtualFile): VirtualFile? {
        return ngxFiles.values.find { ngx -> ngx.originalFiles.contains(file) }
    }
}