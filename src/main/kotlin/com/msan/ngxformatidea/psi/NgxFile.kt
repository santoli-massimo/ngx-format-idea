package com.msan.ngxformatidea.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider
import com.msan.ngxformatidea.language.NgxLanguage


class NgxFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, NgxLanguage.INSTANCE) {
    override fun getFileType(): NgxFileType {
        return NgxFileType.INSTANCE
    }

    override fun toString(): String {
        return "Ngx File"
    }
}