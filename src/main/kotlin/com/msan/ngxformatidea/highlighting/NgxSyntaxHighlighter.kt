package com.msan.ngxformatidea.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.msan.ngxformatidea.lexer.NgxLexerAdapter
import com.msan.ngxformatidea.psi.NgxTypes
import com.msan.ngxformatidea.utils.Logger


class NgxSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer { return NgxLexerAdapter() }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
//        Logger.warn("tokenType: $tokenType")

        return when (tokenType) {
            NgxTypes.STYLE_BLOCK -> pack(STYLE_BLOCK)
//            NgxTypes.STYLE_START -> pack(STYLE_START)
//            NgxTypes.STYLE_END -> pack(STYLE_END)
//            NgxTypes.STYLE -> pack(STYLE)

            NgxTypes.TEMPLATE_BLOCK -> pack(TEMPLATE_BLOCK)
//            NgxTypes.TEMPLATE_START -> pack(TEMPLATE_START)
//            NgxTypes.TEMPLATE_END -> pack(TEMPLATE_END)
//            NgxTypes.TEMPLATE -> pack(TEMPLATE)

            NgxTypes.COMPONENT_BLOCK -> pack(COMPONENT_BLOCK)
//            NgxTypes.COMPONENT_START -> pack(COMPONENT_START)
//            NgxTypes.COMPONENT_END -> pack(COMPONENT_END)
//            NgxTypes.COMPONENT -> pack(COMPONENT)
            else -> emptyArray()
        }
    }

    companion object {
        val TEMPLATE_START: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE_START",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val TEMPLATE_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE_END",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val TEMPLATE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val TEMPLATE_BLOCK: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE_BLOCK",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        val STYLE_START: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE_START",
            DefaultLanguageHighlighterColors.STRING
        )

        val STYLE_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE_END",
            DefaultLanguageHighlighterColors.STRING
        )

        val STYLE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE",
            DefaultLanguageHighlighterColors.STRING
        )

        val STYLE_BLOCK: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE_BLOCK",
            DefaultLanguageHighlighterColors.STRING
        )

        val COMPONENT_START: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_START",
            DefaultLanguageHighlighterColors.INSTANCE_METHOD
        )
        val COMPONENT_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_END",
            DefaultLanguageHighlighterColors.INSTANCE_METHOD
        )
        val COMPONENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT",
            DefaultLanguageHighlighterColors.INSTANCE_METHOD
        )

        val COMPONENT_BLOCK: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_BLOCK",
            DefaultLanguageHighlighterColors.INSTANCE_METHOD
        )
    }
}
