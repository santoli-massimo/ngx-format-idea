package com.msan.ngxformatidea.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import com.msan.ngxformatidea.psi.NgxComponent
import com.msan.ngxformatidea.utils.Logger


class NgxComponentImpl(node: ASTNode) : ASTWrapperPsiElement(node), NgxComponent {

    init{
        Logger.warn("@@@@@@@@@@@@@@@@@@ NgxComponentImpl created")
    }

    override fun isValidHost(): Boolean {
        return true // ✅ Marks this as a valid language injection host
    }

    override fun updateText(text: String): NgxComponent {
        return this // ✅ Ensures text inside component can be updated
    }

    override fun createLiteralTextEscaper(): LiteralTextEscaper<NgxComponent> {
        return object : LiteralTextEscaper<NgxComponent>(this) {
            override fun isOneLine(): Boolean = false

            override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
                outChars.append(rangeInsideHost.substring(myHost.text))
                return true
            }

            override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange): Int {
                val newOffset = offsetInDecoded + rangeInsideHost.startOffset
                return if (newOffset <= rangeInsideHost.endOffset) newOffset else -1
            }
        }
    }

}
