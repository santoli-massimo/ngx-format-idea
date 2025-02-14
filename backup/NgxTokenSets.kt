package com.msan.ngxformatidea.psi

import com.intellij.psi.tree.TokenSet


interface NgxTokenSets {
    companion object {
        val COMPONENT = TokenSet.create(NgxTypes.COMPONENT)
//        val LINE_COMMENTS = TokenSet.create(NgxTypes.LINE_COMMENT)
//        val BLOCK_COMMENTS = TokenSet.create(NgxTypes.BLOCK_COMMENT)
        val COMMENT = TokenSet.create(NgxTypes.COMMENT)
//        val WHITESPACE = TokenSet.create(NgxTypes.WHITE_SPACE)

//        val COMPONENT_START = NgxElementType("COMPONENT_START")
//        val COMPONENT_END = NgxElementType("COMPONENT_END")
//        val TEXT = NgxElementType("TEXT")
//        val WHITESPACE = TokenSet.create(NgxTypes.WHITE_SPACE)
//        val LINE_COMMENT = TokenSet.create(NgxTypes.LINE_COMMENT)

//        val COMPONENT = TokenSet.create(NgxTypes.COMPONENT)
//        val COMPONENT_START = TokenSet.create(NgxTypes.COMPONENT_START)
//        val CONTENT = TokenSet.create(NgxTypes.CONTENT)
//        val COMPONENT_ENDS = TokenSet.create(NgxTypes.COMPONENT_END)
//        val TEXTS = TokenSet.create(NgxTypes.INSTANCE.TEXT)
//        val WHITESPACES = TokenSet.create(NgxTypes.INSTANCE.WHITESPACE)
//        val LINE_COMMENTS = TokenSet.create(NgxTypes.INSTANCE.LINE_COMMENT)
//        val BLOCK_COMMENTS = TokenSet.create(NgxTypes.INSTANCE.BLOCK_COMMENT)
//        val COMMENTS = TokenSet.create(NgxTypes.LINE_COMMENT)
    }
}