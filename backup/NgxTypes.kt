package com.msan.ngxformatidea.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.msan.ngxformatidea.psi.impl.*


object NgxTypes {

    @JvmField val BLOCK_COMMENT: IElementType = NgxElementType("BLOCK_COMMENT")
    @JvmField val COMMENT: IElementType = NgxElementType("COMMENT")
    @JvmField val COMPONENT: IElementType = NgxElementType("COMPONENT")
    @JvmField val LINE_COMMENT: IElementType = NgxElementType("LINE_COMMENT")
    @JvmField val WHITE_SPACE: IElementType = NgxElementType("WHITE_SPACE")

    @JvmField val BLOCK_COMMENT_BLOCK: IElementType = NgxTokenType("BLOCK_COMMENT_BLOCK")
    @JvmField val COMPONENT_BLOCK: IElementType = NgxTokenType("COMPONENT_BLOCK")
    @JvmField val LINE_COMMENT_BLOCK: IElementType = NgxTokenType("LINE_COMMENT_BLOCK")
    @JvmField val WHITE_SPACE_BLOCK: IElementType = NgxTokenType("WHITE_SPACE_BLOCK")

    object Factory {

        fun createElement(node: ASTNode): PsiElement {
            val type = node.elementType
            if (type === BLOCK_COMMENT) {
                return NgxBlockCommentImpl(node)
            } else if (type === COMMENT) {
                return NgxCommentImpl(node)
            } else if (type === COMPONENT) {
                return NgxComponentImpl(node)
            } else if (type === LINE_COMMENT) {
                return NgxLineCommentImpl(node)
            } else if (type === WHITE_SPACE) {
                return NgxWhiteSpaceImpl(node)
            }
            throw AssertionError("Unknown element type: $type")
        }
    }
}