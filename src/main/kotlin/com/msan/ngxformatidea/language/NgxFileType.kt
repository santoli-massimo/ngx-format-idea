package com.msan.ngxformatidea.language

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

//object NgxFileType : LanguageFileType(NgxLanguage) {
//    override fun getName() = "NGX File"
//    override fun getDescription() = "NGX custom language file"
//    override fun getDefaultExtension() = "ngx"
//    override fun getIcon(): Icon? = null
//}


//class NgxFileType private constructor() : LanguageFileType(NgxLanguage) {
class NgxFileType private constructor() : LanguageFileType(NgxLanguage) {
    override fun getName(): String {
        return "Ngx File"
    }

    override fun getDescription(): String {
        return "Angular NGX template file"
    }

    override fun getDefaultExtension(): String {
        return "ngx"
    }

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.JavaScript
    }

    companion object {
        val INSTANCE: NgxFileType = NgxFileType()
    }
}