package com.msan.ngxformatidea.injection

import com.intellij.lang.html.HTMLLanguage
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.openapi.util.TextRange
import com.msan.ngxformatidea.psi.ComponentContentPsiElement
import com.msan.ngxformatidea.utils.Logger


class NgxHtmlInjector : MultiHostInjector {
    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
        return listOf(PsiLanguageInjectionHost::class.java)
    }


    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        Logger.warn("Injecting into ${context.text}")

        if (context is ComponentContentPsiElement) {
            registrar.startInjecting(HTMLLanguage.INSTANCE)
                .addPlace("", "", context as PsiLanguageInjectionHost, TextRange.allOf(context.text))
                .doneInjecting()
        }
    }
}