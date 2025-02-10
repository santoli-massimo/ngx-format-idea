package com.msan.ngxformatidea.language

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class NgxFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, NgxLanguage) {
    override fun getFileType(): FileType = NgxFileType.INSTANCE
}