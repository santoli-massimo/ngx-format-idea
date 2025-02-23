package com.msan.ngxformatidea.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.css.CSSLanguage
import com.intellij.psi.PsiElement
import com.msan.ngxformatidea.psi.impl.base.NgxPsiInjectionHostMixin


abstract class NgxStyleMixin(node: ASTNode) : NgxPsiInjectionHostMixin(node) {
    override val implClass: Class<out PsiElement> = NgxStyleImpl::class.java
    override val injectionLanguage: Language = CSSLanguage.INSTANCE

    override val fileName: String = "style.css"
    override val startMarker: String = "[style]"
    override val endMarker: String = "[/style]"
}

