package com.msan.ngxformatidea.psi

import com.intellij.psi.tree.TokenSet


interface NgxTokenSets {
    companion object {
//        val COMPONENT = NgxElementType("COMPONENT")
//        val COMPONENT_START = NgxElementType("COMPONENT_START")
//        val COMPONENT_END = NgxElementType("COMPONENT_END")
//        val TEXT = NgxElementType("TEXT")
//        val WHITESPACE = NgxElementType("WHITESPACE")
//        val LINE_COMMENT = NgxElementType("LINE_COMMENT")
//        val BLOCK_COMMENT = NgxElementType("BLOCK_COMMENT")

//        val COMPONENTS = TokenSet.create(NgxTypes.INSTANCE.COMPONENT)
        val COMPONENT_START = TokenSet.create(NgxTypes.COMPONENT_START)
//        val COMPONENT_ENDS = TokenSet.create(NgxTypes.INSTANCE.COMPONENT_END)
//        val TEXTS = TokenSet.create(NgxTypes.INSTANCE.TEXT)
//        val WHITESPACES = TokenSet.create(NgxTypes.INSTANCE.WHITESPACE)
//        val LINE_COMMENTS = TokenSet.create(NgxTypes.INSTANCE.LINE_COMMENT)
//        val BLOCK_COMMENTS = TokenSet.create(NgxTypes.INSTANCE.BLOCK_COMMENT)
        val COMMENTS = TokenSet.create(NgxTypes.LINE_COMMENT)
    }
}