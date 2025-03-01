package com.msan.ngxformatidea.vfs

import com.msan.ngxformatidea.utils.Logger
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.msan.ngxformatidea.vfs.listeners.NgxFileListener
import com.msan.ngxformatidea.vfs.listeners.NgxDocumentListener
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.msan.ngxformatidea.vfs.listeners.OriginalFileListener
import com.intellij.openapi.fileEditor.FileDocumentManager


@Service(Service.Level.PROJECT)
class NgxFileManager(private val project: Project) {
    private val ngxFiles = mutableMapOf<String, NgxVirtualFile>()

    companion object {
        fun getInstance(project: Project): NgxFileManager { return project.getService(NgxFileManager::class.java) }
    }

    init {
        // @TODO: @PERFORMANCE: check for those listeners/subscription removal after the ngx file editor is closed
        // Subscribe to ngx document changes
//        val documentManager = EditorFactory.getInstance()
//        documentManager.eventMulticaster.addDocumentListener(NgxDocumentListener(project), project)

//        documentManager.eventMulticaster.addDocumentListener(OriginalFileListener(project), project)


//        // Subscribe to original file changes
//        val connection = project.messageBus.connect()
//        ApplicationManager.getApplication().invokeLater {
//            // @IMPORTANT: Wrapping the subscription in invokeLater is mandatory
//            // to avoid a deadlock (com.intellij.platform.instanceContainer.CycleInitializationException)
//            connection.subscribe(VirtualFileManager.VFS_CHANGES, NgxFileListener(project))
//        }
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


//class AngularComponentFileManager(private val project: Project) {
//    private val virtualFiles = mutableMapOf<String, NgxVirtualFile>()
//
//    fun getOrCreateVirtualFile(baseName: String, componentFiles: List<VirtualFile>): NgxVirtualFile {
//        return virtualFiles.getOrPut(baseName) {
//            val file = virtualFiles[baseName] ?: NgxVirtualFile(componentFiles, "$baseName.ngx", project)
//            file.updateContent()
//            file
//        }
//    }
//
//    fun refreshFileSystem() {
//        VirtualFileManager.getInstance().refreshWithoutFileWatcher(true)
//    }
//}