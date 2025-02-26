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
            NgxColors.TEMPLATE
        )

        val TEMPLATE_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE_END",
            NgxColors.TEMPLATE
        )

        val TEMPLATE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE",
            NgxColors.TEMPLATE
        )
        val TEMPLATE_BLOCK: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "TEMPLATE_BLOCK",
            NgxColors.TEMPLATE
        )

        val STYLE_START: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE_START",
            NgxColors.STYLE
        )

        val STYLE_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE_END",
            NgxColors.STYLE
        )

        val STYLE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE",
            NgxColors.STYLE
        )

        val STYLE_BLOCK: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "STYLE_BLOCK",
            NgxColors.STYLE
        )

        val COMPONENT_START: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_START",
            NgxColors.COMPONENT
        )
        val COMPONENT_END: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_END",
            NgxColors.COMPONENT
        )
        val COMPONENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT",
            NgxColors.COMPONENT
        )

        val COMPONENT_BLOCK: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "COMPONENT_BLOCK",
            NgxColors.COMPONENT
        )
    }
}
