package com.msan.ngxformatidea.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.msan.ngxformatidea.psi.impl.NgxStyleImpl
import com.msan.ngxformatidea.psi.impl.NgxTemplateImpl
import com.msan.ngxformatidea.psi.impl.NgxComponentImpl
import com.intellij.psi.PsiFileFactory


class NgxElementFactory private constructor(private val project: Project) {
    companion object {
        fun getInstance(project: Project): NgxElementFactory {
            return NgxElementFactory(project)
        }
    }

    fun createFromText(text: String, elementClass: Class<*>): PsiElement {
        val file = createFile(text)
        return when {
            NgxTemplateImpl::class.java.isAssignableFrom(elementClass) -> file.findChildByClass(NgxTemplateImpl::class.java)!!
            NgxStyleImpl::class.java.isAssignableFrom(elementClass) -> file.findChildByClass(NgxStyleImpl::class.java)!!
            NgxComponentImpl::class.java.isAssignableFrom(elementClass) -> file.findChildByClass(NgxComponentImpl::class.java)!!
            else -> throw IllegalArgumentException("Unsupported element class: ${elementClass.name}")
        }
    }

    private fun createFile(text: String): NgxFile {
        val name = "dummy.ngx"
        return PsiFileFactory.getInstance(project)
            .createFileFromText(name, NgxFileType.INSTANCE, text) as NgxFile
    }
}