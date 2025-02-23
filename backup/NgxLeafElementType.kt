package com.msan.ngxformatidea.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILeafElementType
import com.msan.ngxformatidea.language.NgxLanguage


class NgxLeafElementType(debugName: String): IElementType(debugName, NgxLanguage.INSTANCE), ILeafElementType {
    override fun createLeafNode(charSequence: CharSequence): ASTNode {
        return OuterLanguageElementImpl(this, charSequence)
    }
}