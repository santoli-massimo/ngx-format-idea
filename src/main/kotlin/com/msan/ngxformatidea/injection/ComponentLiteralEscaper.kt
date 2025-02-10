package com.msan.ngxformatidea.injection

import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import com.msan.ngxformatidea.psi.ComponentContentPsiElement

class ComponentLiteralEscaper(host: ComponentContentPsiElement) : LiteralTextEscaper<ComponentContentPsiElement>(host) {
    override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder) = true

    override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange) = rangeInsideHost.startOffset + offsetInDecoded

    override fun isOneLine() = false
}