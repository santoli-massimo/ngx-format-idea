package com.msan.ngxformatidea.lexer
import com.intellij.lexer.FlexAdapter
import com.intellij.psi.tree.IElementType
import com.msan.ngxformatidea.utils.Logger


class NgxLexerAdapter : FlexAdapter(NgxLexer()){
    override fun advance() {
        val tokenType: IElementType? = tokenType
//        Logger.warn("########### Lexer advanced: ${tokenType}")
//        Logger.warn("########### Lexer advanced: ${tokenType.toString()}")

        val tokenString = tokenType.toString()
        if(
            tokenString != "BAD_CHARACTER"
            && tokenString != "WHITE_SPACE"
            && tokenString != "LINE_COMMENT"
            && tokenString != "TEXT"
        ) {
            Logger.warn("########### Lexer advanced: ${tokenType?.toString() ?: "NULL"}")
        }
        else if(tokenType.toString() == "WHITE_SPACE"){
            Logger.warn("-----")
        }

        super.advance()
    }
}