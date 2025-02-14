package com.msan.ngxformatidea.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.msan.ngxformatidea.psi.impl.NgxCommentImpl
import com.msan.ngxformatidea.psi.impl.NgxComponentEndImpl
import com.msan.ngxformatidea.psi.impl.NgxComponentImpl
import com.msan.ngxformatidea.psi.impl.NgxComponentStartImpl
import com.msan.ngxformatidea.psi.impl.NgxContentImpl
import com.msan.ngxformatidea.psi.impl.NgxLineCommentImpl
import com.msan.ngxformatidea.psi.impl.NgxWhiteSpaceImpl


object NgxTypes {
    @JvmField val COMPONENT: IElementType = NgxTokenType("COMPONENT")
    @JvmField val CONTENT: IElementType = NgxTokenType("CONTENT")
    @JvmField val COMMENT: IElementType = NgxTokenType("COMMENT")

    @JvmField val COMPONENT_START: IElementType = NgxTokenType("COMPONENT_START")
    @JvmField val COMPONENT_END: IElementType = NgxTokenType("COMPONENT_END")
    @JvmField val LINE_COMMENT: IElementType = NgxTokenType("LINE_COMMENT")
    @JvmField val TEXT: IElementType = NgxTokenType("TEXT")
    @JvmField val WHITE_SPACE: IElementType = NgxTokenType("WHITE_SPACE")
    @JvmField val LINE_TERMINATOR: IElementType = NgxTokenType("LINE_TERMINATOR")

    val TEXT_TOKENS: TokenSet = TokenSet.create(TEXT)

    object Factory {
        @JvmStatic
        fun createElement(node: ASTNode): PsiElement {
//            Logger.warn("+++++++ NgxTypes.createElement +++++++++ ${node.elementType}")

            return when (node.elementType) {
                COMPONENT_START -> NgxComponentStartImpl(node)
                CONTENT -> NgxContentImpl(node)
                COMPONENT_END -> NgxComponentEndImpl(node)
                COMPONENT -> NgxComponentImpl(node)
                LINE_COMMENT -> NgxLineCommentImpl(node)
                COMMENT -> NgxCommentImpl(node)
                WHITE_SPACE -> NgxWhiteSpaceImpl(node)
                LINE_TERMINATOR -> NgxLineCommentImpl(node)
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




