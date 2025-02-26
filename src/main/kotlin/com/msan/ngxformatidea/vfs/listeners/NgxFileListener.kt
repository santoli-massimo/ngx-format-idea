package com.msan.ngxformatidea.vfs.listeners

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFileEvent
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.msan.ngxformatidea.vfs.NgxFileManager
import java.util.concurrent.atomic.AtomicBoolean
import com.msan.ngxformatidea.utils.Logger
import com.msan.ngxformatidea.vfs.NgxVirtualFile
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent


object SyncLock {
    private val syncInProgress = AtomicBoolean(false)

    fun <T> withLock(action: () -> T): T? {
        if (syncInProgress.compareAndSet(false, true)) {
            try {
                return action()
            } finally {
                syncInProgress.set(false)
            }
        }
        return null
    }

    fun isSyncInProgress(): Boolean = syncInProgress.get()
}

class NgxFileListener(private val project: Project) : BulkFileListener, VirtualFileListener {
    private val fileManager = NgxFileManager.getInstance(project)


    override fun contentsChanged(event: VirtualFileEvent) {
        Logger.warn("contentsChanged: $event")

        if (SyncLock.isSyncInProgress()) return

        SyncLock.withLock {
            val file = event.file
            fileManager.findAssociatedVirtualFile(file)?.let { ngxFile ->
                (ngxFile as? NgxVirtualFile)?.updateContent()
            }
        }
        val file = event.file
        fileManager.findAssociatedVirtualFile(file)?.let { ngxFile ->
            if (ngxFile is NgxVirtualFile && !ngxFile.isUpdating) {
                ApplicationManager.getApplication().invokeLater {
                    ngxFile.updateContent()
                }
            }
        }
    }

    override fun after(events: List<VFileEvent>) {
        Logger.warn("after: $events")

        for (event in events) {
            if (event is VFileContentChangeEvent) {
                val file = event.file
                fileManager.findAssociatedVirtualFile(file)?.let { ngxFile ->
                    if (ngxFile is NgxVirtualFile && !ngxFile.isUpdating) {
                        ApplicationManager.getApplication().invokeLater {
                            ngxFile.updateContent()
                        }
                    }
                }
            }
        }
    }
}