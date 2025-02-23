package com.msan.ngxformatidea.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.html.HTMLLanguage
import com.intellij.psi.PsiElement
import com.msan.ngxformatidea.psi.impl.base.NgxPsiInjectionHostMixin


abstract class NgxTemplateMixin(node: ASTNode) : NgxPsiInjectionHostMixin(node) {
    override val implClass: Class<out PsiElement> = NgxTemplateImpl::class.java
    override val injectionLanguage: Language = HTMLLanguage.INSTANCE

    override val fileName: String = "template.html"
    override val startMarker: String = "[template]"
    override val endMarker: String = "[/template]"
}

