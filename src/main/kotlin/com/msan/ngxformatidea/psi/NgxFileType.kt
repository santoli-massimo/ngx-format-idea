package com.msan.ngxformatidea.psi

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import com.msan.ngxformatidea.language.NgxLanguage
import com.msan.ngxformatidea.utils.NgxFileIconProvider
import javax.swing.Icon


class NgxFileType private constructor() : LanguageFileType(NgxLanguage.INSTANCE) {
    override fun getName(): String {
        return "Ngx File"
    }

    override fun getDescription(): String {
        return "Ngx template file"
    }

    override fun getDefaultExtension(): String {
        return "ngx"
    }

    override fun getIcon(): Icon {
        return IconLoader.getIcon("icons/angular2.svg", NgxFileIconProvider::class.java)
    }

    companion object {
        @JvmStatic
        val INSTANCE: NgxFileType = NgxFileType()
    }
}