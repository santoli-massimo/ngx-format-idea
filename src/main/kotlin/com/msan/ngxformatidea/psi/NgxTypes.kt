package com.msan.ngxformatidea.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.msan.ngxformatidea.psi.impl.NgxComponentImpl
import com.msan.ngxformatidea.psi.impl.NgxContentImpl


object NgxTypes {
    @JvmField val COMPONENT: IElementType = NgxElementType("COMPONENT")
    @JvmField val CONTENT: IElementType = NgxElementType("CONTENT")

    @JvmField val COMPONENT_START: IElementType = NgxTokenType("COMPONENT_START")
    @JvmField val COMPONENT_END: IElementType = NgxTokenType("COMPONENT_END")
    @JvmField val LINE_COMMENT: IElementType = NgxTokenType("LINE_COMMENT")
    @JvmField val TEXT: IElementType = NgxTokenType("TEXT")

    val TEXT_TOKENS: TokenSet = TokenSet.create(TEXT)

    object Factory {
        @JvmStatic
        fun createElement(node: ASTNode): PsiElement {
            return when (node.elementType) {
                COMPONENT_START -> NgxComponentImpl(node)
                COMPONENT_END -> NgxComponentImpl(node)
                COMPONENT -> NgxComponentImpl(node)
                CONTENT -> NgxContentImpl(node)
                else -> throw AssertionError("Unknown element type: ${node.elementType}")
            }
        }
    }
}


//class NgxTypes private constructor() {
//
//    @JvmField val COMPONENT_START = NgxTokenType("COMPONENT_START")
//    @JvmField val COMPONENT_END = NgxTokenType("COMPONENT_END")
//    @JvmField val TEXT = NgxTokenType("TEXT")
//    @JvmField val WHITESPACE = NgxTokenType("WHITESPACE")
//    @JvmField val LINE_COMMENT = NgxTokenType("LINE_COMMENT")
//    @JvmField val BLOCK_COMMENT = NgxTokenType("BLOCK_COMMENT")
//
//    companion object {
//        @JvmStatic
//        val INSTANCE: NgxTypes = NgxTypes()
//    }
//}




