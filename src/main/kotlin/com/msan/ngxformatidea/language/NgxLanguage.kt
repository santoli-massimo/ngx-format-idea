package com.msan.ngxformatidea.language
import com.intellij.lang.Language

class NgxLanguage private constructor() : Language("Ngx") {
    companion object {
        @JvmStatic
        val INSTANCE: NgxLanguage = NgxLanguage()
   }
   private fun readResolve(): Any = NgxLanguage
   override fun isCaseSensitive() = true
   override fun getDisplayName() = "Ngx"
}

//object NgxLanguage : Language("Ngx") {
//   private fun readResolve(): Any = NgxLanguage
//   override fun isCaseSensitive() = true
//   override fun getDisplayName() = "Ngx"
//
//}

//object NgxLanguage : Language("NGX")


//object NgxLanguage : Language("Ngx") {
//    override fun isCaseSensitive() = true
//}





