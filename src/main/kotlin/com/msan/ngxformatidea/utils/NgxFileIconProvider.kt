package com.msan.ngxformatidea.utils

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class NgxFileIconProvider : FileIconProvider {
    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {
        if (file.name.endsWith(".component.ngx")) {
            return IconLoader.getIcon("icons/angular2.svg", NgxFileIconProvider::class.java)
//            val tsFileType = FileTypeManager.getInstance().getFileTypeByExtension("ts")
//            return tsFileType.icon
        }
        return null
    }
}


