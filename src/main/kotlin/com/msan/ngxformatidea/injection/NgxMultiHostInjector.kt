package com.msan.ngxformatidea.injection

import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.msan.ngxformatidea.psi.impl.base.NgxPsiInjectionHostMixin


class NgxMultiHostInjector : MultiHostInjector {
    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
        return listOf(NgxPsiInjectionHostMixin::class.java)
//        return listOf(PsiLanguageInjectionHost::class.java)
//        return listOf(PsiElement::class.java)
    }

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        val injectionHost = context as NgxPsiInjectionHostMixin
        injectLanguage(context.startMarker, context.endMarker, context.injectionLanguage, registrar, injectionHost)
    }

    private fun injectLanguage(
        startMarker: String,
        endMarker: String,
        language: Language,
        registrar: MultiHostRegistrar,
        injectionHost: NgxPsiInjectionHostMixin,
        prefix: String? = null,
        suffix: String? = null
    ){
        val ranges = getRangeFor(injectionHost.text, startMarker, endMarker) ?: return
        registrar.startInjecting(language)
            .addPlace(prefix, suffix, injectionHost, ranges)
            .doneInjecting()
    }

    fun getRangeFor(text: String, startTag: String, endTag: String): TextRange? {
        val startIndex = text.indexOf(startTag)
        val endIndex = text.indexOf(endTag)

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) { return null }

        val contentStart = startIndex + startTag.length
        val contentEnd = endIndex

        return TextRange(contentStart, contentEnd)
    }
}