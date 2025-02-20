package com.msan.ngxformatidea.lexer
import com.intellij.lexer.FlexAdapter
import com.intellij.psi.tree.IElementType
import com.msan.ngxformatidea.utils.Logger


class NgxLexerAdapter : FlexAdapter(NgxLexer()){
//    override fun advance() {
//        val tokenType: IElementType? = tokenType
//
//        val tokenString = tokenType.toString()
//        if(
//            tokenString.contains("TEMPLATE") || tokenString.contains("STYLE")
//        ) {
//            Logger.warn("########### Lexer advanced: ${tokenType?.toString() ?: "NULL"}")
//        }
//
//        super.advance()
//    }
}