package com.msan.ngxformatidea.language

import com.intellij.lang.Language;
import com.intellij.lang.javascript.JavaScriptSupportLoader.TYPESCRIPT

//val typeScriptLanguage: Language = Language.findLanguageByID("TypeScript") ?: Language.ANY

class NgxLanguage private constructor() : Language(
//    TYPESCRIPT,
    "Ngx"
) {
    companion object {
        @JvmStatic
        val INSTANCE: NgxLanguage = NgxLanguage()

        @JvmStatic
        val Angular2Html: Language = Language.findLanguageByID("Angular2Html") ?: Language.ANY
   }
   private fun readResolve(): Any = NgxLanguage
   override fun isCaseSensitive() = true
   override fun getDisplayName() = "Ngx"
}




