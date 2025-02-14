package com.msan.ngxformatidea.psi

import com.intellij.psi.PsiElement


//interface NgxComponent : PsiElement, PsiLanguageInjectionHost{
interface NgxComponent : PsiElement {
    fun getComponentEnd(): NgxComponentEnd?

    fun getComponentStart(): NgxComponentStart?

    fun getContent(): NgxContent?
}


//interface NgxComponent : PsiLanguageInjectionHost{
//    override fun isValidHost(): Boolean {
//        return true // Allows language injection in this element
//    }
//
//    override fun createLiteralTextEscaper(): LiteralTextEscaper<NgxComponent> {
//        return object : LiteralTextEscaper<NgxComponent>(this) {
//            override fun isOneLine(): Boolean = false
//
//            override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
//                outChars.append(rangeInsideHost.substring(myHost.text))
//                return true
//            }
//
//            override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange): Int {
//                val newOffset = offsetInDecoded + rangeInsideHost.startOffset
//                return if (newOffset <= rangeInsideHost.endOffset) newOffset else -1
//            }
//        }
//    }
//
//    override fun updateText(text: String): PsiLanguageInjectionHost {
//        return this
//    }
//
//    fun getComponentStart(): NgxComponentStart?
//
//    fun getComponentEnd(): NgxComponentEnd?
//
//    fun getContent(): NgxContent?
//}
