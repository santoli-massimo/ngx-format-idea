package com.msan.ngxformatidea.highlighting


import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.msan.ngxformatidea.lexer.NgxLexerAdapter
import com.msan.ngxformatidea.psi.NgxTypes


class NgxSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return NgxLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
//        Logger.warn("Highlighting token (getTokenHighlights): $tokenType")
        return when (tokenType) {
            NgxTypes.COMPONENT_START -> arrayOf(COMPONENT_START)
            NgxTypes.CONTENT -> arrayOf(CONTENT)
            NgxTypes.COMPONENT_END -> arrayOf(COMPONENT_END)
//            NgxTypes.LINE_COMMENT -> arrayOf(LINE_COMMENT)
//            NgxTypes.COMMENT -> arrayOf(COMMENT)
//            NgxTypes.CODE_FRAGMENT -> arrayOf(CODE_FRAGMENT)
            NgxTypes.COMPONENT -> arrayOf(COMPONENT)
            else -> emptyArray()
        }
    }

    companion object {

        val COMPONENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT",
            DefaultLanguageHighlighterColors.MARKUP_TAG
        )

        val COMMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        val CONTENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CONTENT",
            DefaultLanguageHighlighterColors.MARKUP_TAG
        )

        val CODE_FRAGMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "CODE_FRAGMENT",
            DefaultLanguageHighlighterColors.INTERFACE_NAME
        )

        val COMPONENT_START: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_START",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val COMPONENT_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_END",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val LINE_COMMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "NGX_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        val BLOCK_COMMENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "NGX_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        val TEXT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEXT",
            DefaultLanguageHighlighterColors.STRING
        )

        val WHITESPACE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "WHITESPACE",
            DefaultLanguageHighlighterColors.STRING
        )
    }
}
