package com.msan.ngxformatidea.psi.impl
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.msan.ngxformatidea.psi.impl.base.NgxPsiInjectionHostMixin
import com.intellij.lang.javascript.JavaScriptSupportLoader.TYPESCRIPT
import com.intellij.psi.PsiElement

abstract class NgxComponentMixin(node: ASTNode) : NgxPsiInjectionHostMixin(node) {
    override val implClass: Class<out PsiElement> = NgxComponentImpl::class.java
    override val injectionLanguage: Language = TYPESCRIPT

    override val fileName: String = "component.ts"
    override val startMarker: String = "[component]"
    override val endMarker: String = "[/component]"
}
