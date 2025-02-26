package com.msan.ngxformatidea.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.ui.JBColor
import java.awt.Color
import java.awt.Font


object NgxColors {
    val TEMPLATE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "NGX.TEMPLATE",
        TextAttributes().apply {
            foregroundColor = JBColor(
//                Color(255, 255, 255),
//                Color(255, 255, 255),
                Color(165, 0, 70,),
                Color(255, 0, 105),
            )
            fontType = Font.BOLD
//            effectType = EffectType.LINE_UNDERSCORE
//            effectColor = JBColor(
//                Color(255, 0, 105),
//                Color(165, 0, 70,),
//            )
        }
    )

    val STYLE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "NGX.STYLE",
        TextAttributes().apply {
            foregroundColor = JBColor(
                Color(200, 140, 60),
                Color(230, 190, 120),
            )
            fontType = Font.BOLD
        }
    )

    val COMPONENT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "NGX.COMPONENT",
        TextAttributes().apply {
            foregroundColor = JBColor(
                Color(45, 130, 140),
                Color(45, 190, 200),
            )
            fontType = Font.BOLD
        }
    )
}