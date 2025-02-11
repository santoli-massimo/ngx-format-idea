package com.msan.ngxformatidea.grammar

import com.intellij.lexer.FlexAdapter
import com.intellij.psi.tree.IElementType
import com.msan.ngxformatidea.utils.Logger


class NgxLexerAdapter : FlexAdapter(_NgxLexer()){
    override fun advance() {
        val tokenType: IElementType? = tokenType
//        Logger.warn("########### Lexer advanced: ${tokenType?.toString() ?: "NULL"}")

        if(tokenType?.toString() != "BAD_CHARACTER" && tokenType?.toString() != "WHITE_SPACE") {
            Logger.warn("########### Lexer advanced: ${tokenType?.toString() ?: "NULL"}")
        }
        else if(tokenType.toString() == "WHITE_SPACE"){
            Logger.warn("-----")
        }

        super.advance()
    }
}