package com.msan.ngxformatidea.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiLanguageInjectionHost
import com.msan.ngxformatidea.injection.ComponentLiteralEscaper

class ComponentContentPsiElement(node: ASTNode) : ASTWrapperPsiElement(node), PsiLanguageInjectionHost {
    override fun isValidHost() = true

    override fun updateText(text: String): PsiLanguageInjectionHost {
        val newNode = node.treeParent
        replace(newNode.psi)
        return this
    }

    override fun createLiteralTextEscaper() = ComponentLiteralEscaper(this)
}