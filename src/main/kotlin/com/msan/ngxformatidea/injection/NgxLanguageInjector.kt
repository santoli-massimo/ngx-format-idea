package com.msan.ngxformatidea.injection

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.lang.Language
import com.intellij.psi.InjectedLanguagePlaces
import com.intellij.psi.LanguageInjector
import com.msan.ngxformatidea.psi.NgxComponent
import com.msan.ngxformatidea.utils.Logger

class NgxLanguageInjector : LanguageInjector {
    override fun getLanguagesToInject(
        host: PsiLanguageInjectionHost,
        injectionPlacesRegistrar: InjectedLanguagePlaces
    ) {

        val hostText = host.containingFile
        val virtualFile = host.containingFile?.virtualFile ?: return

        if (!virtualFile.name.endsWith(".ngx")) return

        Logger.warn("################# INJECTION #######################")
        Logger.warn("Injecting into ${virtualFile.name}")

        val html = Language.findLanguageByID("HTML") ?: return
        val textRange = host.textRange ?: return

        if (host is NgxComponent) {
            injectionPlacesRegistrar.addPlace(
                html,
                textRange,
                null,
                null
            )
        }

    }

}
