package com.msan.ngxformatidea.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.msan.ngxformatidea.utils.Logger

abstract class NgxComponentMixin(node: ASTNode) : ASTWrapperPsiElement(node), PsiLanguageInjectionHost {

    override fun isValidHost(): Boolean {
        return true
    }

    override fun updateText(text: String): PsiLanguageInjectionHost {
        return this // Puoi aggiungere logica per aggiornare il testo
    }

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> {
        return object : LiteralTextEscaper<NgxComponentMixin>(this) {

            override fun decode(range: TextRange, outChars: StringBuilder): Boolean {
//                Logger.warn("------- Decoding range: ${range}")
                outChars.append(range.substring(myHost.text))
                return true
            }

            override fun getOffsetInHost(offsetInDecoded: Int, range: TextRange): Int {
                val offset = offsetInDecoded + range.startOffset
//                Logger.warn("------- Offset Mapping: Decoded Offset = ${offsetInDecoded} | Host Offset = ${offset}")

                return if (offset <= range.endOffset) offset else -1
            }

            override fun isOneLine(): Boolean {
                return false // Permette codice multilinea
            }
        }
    }
}
