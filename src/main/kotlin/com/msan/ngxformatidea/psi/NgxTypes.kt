package com.msan.ngxformatidea.psi

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

object NgxTypes {
    val COMPONENT_START = NgxTokenType("COMPONENT_START")
    val COMPONENT_END = NgxTokenType("COMPONENT_END")
    val TEXT = NgxTokenType("TEXT")

    val TEXT_TOKENS: TokenSet = TokenSet.create(TEXT)
}