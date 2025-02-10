package com.msan.ngxformatidea.injection

import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.msan.ngxformatidea.utils.Logger


class NgxMultiHostInjector : MultiHostInjector {
    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
        return listOf(PsiLanguageInjectionHost::class.java)
    }

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        if (!context.isValid) return
        val hostText = context.containingFile
        val virtualFile = context.containingFile?.virtualFile ?: return
//        if(virtualFile.fileType is NgxFileType){
//            Logger.warn("***************** NgxFileType")
//        }

        if (!virtualFile.name.endsWith(".ngx")) return

        Logger.warn("#################################")
        Logger.warn(context.text)

        // Get the correct PSI element containing the full template
        val ranges = getRangeFor(hostText.text, "<ng-component-template>", "</ng-component-template>") ?: return
//        val children = hostText.getChildren()
//        for (child in children) {
//            Logger.warn("###############################################################")
//            Logger.warn("🔎 Child: ${child.text} | Type: ${child.javaClass.simpleName}")
//        }

//        val parent = context.parent
//        if(parent.text.startsWith("<ng-component-template>") && parent.text.endsWith("</ng-component-template>")){
//            Logger.warn("Parent is <ng-component-template>")
//            injectLanguage(HTMLLanguage.INSTANCE, registrar, parent as PsiLanguageInjectionHost, parent.textRange)
//        }


//        val children = hostText.getChildren()
//        val matchingElements = children.filter { child ->
//            val text = child.text
//            text.startsWith("<ng-component-template>") && text.endsWith("</ng-component-template>")
//        }
//        Logger.warn("Found ${matchingElements[0].text}")

//        Logger.warn("Checking context:${context.textRange} - ${context.text}")
//        val host = hostText.findElementAt(ranges.startOffset) as? PsiLanguageInjectionHost ?: return
//        val host = hostText.findElementAt(ranges.startOffset) as? PsiLanguageInjectionHost

//        Logger.warn("Injecting into ${host.text}")
//        Logger.warn("Injecting into ${host}...")

//        injectLanguage(HTMLLanguage.INSTANCE, registrar, context.parent.parent as PsiLanguageInjectionHost, ranges)

//        InjectedLanguagePlaces.addPlace(HTMLLanguage.INSTANCE, ranges, context.containingFile, null)
//        InjectedLanguageManager.getInjectionHost(host)
    }

    private fun injectLanguage(
        language: Language,
        registrar: MultiHostRegistrar,
        host: PsiLanguageInjectionHost,
        textRange: TextRange
    ){

        registrar.startInjecting(language)
            .addPlace(null, null, host, textRange)
            .doneInjecting()
    }

    private fun getRangeFor(
        hostText: String,
        startMarker: String,
        endMarker: String?,
    ): TextRange? {
        val startOffset = hostText.indexOf(startMarker)
        val endOffset = endMarker?.let { hostText.indexOf(it, startOffset) } ?: hostText.length
//        Logger.warn("startOffset: $startOffset, endOffset: $endOffset")

        if (startOffset == -1 || endOffset == -1 || startOffset >= endOffset) { return null }
        val adjustedStart = startOffset + startMarker.length
        val adjustedEnd = adjustedStart + endOffset
        if (adjustedStart >= adjustedEnd) { return null }

//        Logger.warn("Range OK!")

        return TextRange(adjustedStart, adjustedEnd)
    }

//    override fun getLanguagesToInject(registrar: MultiHostRegistrar, host: PsiElement) {
//        if (host !is PsiLanguageInjectionHost || !host.isValid) return
//        val psiFile = host.containingFile ?: return
//        val virtualFile = psiFile.virtualFile ?: return
//
//        if (!virtualFile.name.endsWith(".ngx")) return
//
//        // Get the full template content from the correct PSI root element
//        val parent = findRootTemplateElement(host) ?: host // Default to host if no better match is found
//        val parentText = parent.text
//        val hostRange = parent.textRange
//
//        Logger.warn("Injecting into full template: ${parentText.take(100)}...")
//
//        injectLanguage(registrar, parent, parentText, "<ng-component-template>", "</ng-component-template>", HTMLLanguage.INSTANCE, hostRange)
//    }


    private fun injectLanguage(
        registrar: MultiHostRegistrar,
        host: PsiLanguageInjectionHost,
        hostText: String,
        startMarker: String,
        endMarker: String?,
        language: Language,
        hostRange: TextRange
    ) {
        val startOffset = hostText.indexOf(startMarker)
        val endOffset = endMarker?.let { hostText.indexOf(it, startOffset) } ?: hostText.length

        if (startOffset == -1 || endOffset == -1 || startOffset >= endOffset) {
            Logger.warn("Skipping injection for ${language.displayName} - invalid start/end offsets ($startOffset, $endOffset)")
            return
        }

        val adjustedStart = hostRange.startOffset + startOffset + startMarker.length
        val adjustedEnd = hostRange.startOffset + endOffset

        if (adjustedStart >= adjustedEnd || !hostRange.containsRange(adjustedStart, adjustedEnd)) {
            Logger.warn("Skipping invalid injection for ${language.displayName}: Range ($adjustedStart, $adjustedEnd) out of host range ${hostRange}")
            return
        }

        val rangeInsideHost = TextRange(adjustedStart, adjustedEnd)

        Logger.warn("@@@@@@@@@@ Injecting ${language.displayName} from $rangeInsideHost in host range ${hostRange}")

        registrar.startInjecting(language)
            .addPlace(null, null, host, rangeInsideHost)
            .doneInjecting()
    }
}




//import com.intellij.lang.Language
//import com.intellij.lang.html.HTMLLanguage
//import com.intellij.lang.injection.MultiHostInjector
//import com.intellij.lang.injection.MultiHostRegistrar
//import com.intellij.openapi.util.TextRange
//import com.intellij.psi.PsiElement
//import com.intellij.psi.PsiLanguageInjectionHost
////import com.msan.ngxformatidea.lang.CssLanguage
////import com.msan.ngxformatidea.lang.JavaScriptLanguage
//
//class AngularMultiHostInjector : MultiHostInjector {
//    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
//        return listOf(PsiLanguageInjectionHost::class.java)
//    }
//
//    override fun getLanguagesToInject(registrar: MultiHostRegistrar, host: PsiElement) {
//        if (host !is PsiLanguageInjectionHost || !host.isValid) return
//        val psiFile = host.containingFile ?: return
//        val virtualFile = psiFile.virtualFile ?: return
//
//        if (!virtualFile.name.endsWith(".ngx")) return
//
//        val hostText = host.text
//        val hostRange = host.textRange
//
//        Logger.warn("Injecting body  ${hostText}")
//
//        injectLanguage(registrar, host, hostText, "<ng-component-template>", "</ng-component-template>", HTMLLanguage.INSTANCE, hostRange)
////        injectLanguage(registrar, host, hostText, "<ng-component-style>", "</ng-component-style>", CssLanguage, hostRange)
//    }
//
//    private fun injectLanguage(
//        registrar: MultiHostRegistrar,
//        host: PsiLanguageInjectionHost,
//        hostText: String,
//        startMarker: String,
//        endMarker: String?,
//        language: Language,
//        hostRange: TextRange
//    ) {
//
//
//        val startOffset = hostText.indexOf(startMarker)
//        val endOffset = endMarker?.let { hostText.indexOf(it, startOffset) } ?: hostText.length
//
//        Logger.warn("@@@@@@@@@@ startOffset: $startOffset, endOffset: $endOffset")
//
//
//        if (startOffset == -1 || endOffset == -1 || startOffset >= endOffset) return
//
//        val adjustedStart = hostRange.startOffset + startOffset + startMarker.length
//        val adjustedEnd = hostRange.startOffset + endOffset
//
//        if (adjustedStart >= adjustedEnd || !hostRange.containsRange(adjustedStart, adjustedEnd)) {
//            Logger.warn("Skipping invalid injection for ${language.displayName}: Range ($adjustedStart, $adjustedEnd) out of host range ${hostRange}")
//            return
//        }
//
//        val rangeInsideHost = TextRange(adjustedStart, adjustedEnd)
//
//        Logger.warn("@@@@@@@@@@ Injecting ${language.displayName} from $rangeInsideHost in host range ${hostRange}")
//
//        registrar.startInjecting(language)
//            .addPlace(null, null, host, rangeInsideHost)
//            .doneInjecting()
//    }
//}
//
//
