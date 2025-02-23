package com.msan.ngxformatidea.language

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.css.CSSLanguage
import com.intellij.lang.html.HTMLLanguage
import com.intellij.lang.javascript.JavaScriptSupportLoader.TYPESCRIPT
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider
import com.intellij.psi.PsiManager
import com.intellij.psi.css.impl.CssFileImpl
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.impl.source.html.HtmlFileImpl
import com.msan.ngxformatidea.psi.NgxFile
//import com.msan.ngxformatidea.psi.NgxLeafElementType
import com.msan.ngxformatidea.utils.Logger


class NgxFileViewProvider(
    manager: PsiManager,
    file: VirtualFile,
    eventSystemEnabled: Boolean
) : MultiplePsiFilesPerDocumentFileViewProvider(manager, file, eventSystemEnabled){
    val CACHED_VIEW_PROVIDER: Key<NgxFileViewProvider> = Key<NgxFileViewProvider>("CACHED_VIEW_PROVIDER")

    init{
        virtualFile.putUserData(CACHED_VIEW_PROVIDER, this)
    }

    override fun getLanguages(): Set<Language> {
        return setOf(NgxLanguage.INSTANCE)
//        return setOf(
//            NgxLanguage.INSTANCE,
//            HTMLLanguage.INSTANCE,
//            CSSLanguage.INSTANCE,
//            TYPESCRIPT,
//        )
    }

    override fun getBaseLanguage(): Language{
        return NgxLanguage.INSTANCE
//        return TYPESCRIPT
    }

    // MULTIPLE PSI FILES PER DOCUMENT + TEMPLATE LANGUAGE
//    override fun getTemplateDataLanguage(): Language {
////        return CSSLanguage.INSTANCE
//        return NgxLanguage.INSTANCE
////          return TYPESCRIPT
////          return HTMLLanguage.INSTANCE
//    }

    override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider {
        return NgxFileViewProvider(manager, fileCopy, false)
    }

//    override fun createFile(language: Language): PsiFile? {
//        val parser = LanguageParserDefinitions.INSTANCE.forLanguage(language)
//        return NgxFile(this)
////        Logger.warn("@@@@@@@@@@@@@ createFile: ${language.id}")
//
////        return parser?.createFile(this)
//        return when {
//            language.isKindOf(HTMLLanguage.INSTANCE) -> {
////                Logger.warn("@@@@@@@@@@@@@ createFile: HTML")
//                val outer = NgxLeafElementType("OUTER_TEMPLATE_NGX")
//
////                val dataElement = TemplateDataElementType("Outer TEMPLATE", HTMLLanguage.INSTANCE, NgxTypes.TEMPLATE_CONTENT, outer)
//                val file = parser.createFile(this) as PsiFileImpl
////                file.contentElementType = dataElement
//                return file
//            }
//            language.isKindOf(CSSLanguage.INSTANCE) -> {
////                Logger.warn("@@@@@@@@@@@@@ createFile: CSS")
//                val outer = NgxLeafElementType("OUTER_STYLE_NGX")
//
////                val dataElement = TemplateDataElementType("Outer STYLE", CSSLanguage.INSTANCE, NgxTypes.STYLE_CONTENT, outer)
//                val file = parser?.createFile(this) as PsiFileImpl
////                file.contentElementType = dataElement
//                return file
//            }
//
//            language.isKindOf(NgxLanguage.INSTANCE) -> {
////                Logger.warn("@@@@@@@@@@@@@ createFile: Ngx")
////                return NgxFile(this)
//                return parser?.createFile(this) as PsiFileImpl
//            }
//
//            language.isKindOf(TYPESCRIPT) -> {
////                Logger.warn("@@@@@@@@@@@@@ createFile: Ts")
////                val dataElement = TemplateDataElementType("Outer COMPONENT", TYPESCRIPT, NgxTypes.COMPONENT, outer)
//                val file = parser?.createFile(this) as PsiFileImpl
////                file.contentElementType = dataElement
//                return file
//            }
//            else -> null
//        }
//    }

    override fun createPsiFileImpl(language: Language): PsiFileImpl? {
        Logger.warn("@@@@@@@@@@@@@ createPsiFileImpl: ${language.id}")
        val parser = LanguageParserDefinitions.INSTANCE.forLanguage(language)

        return when {
            language.isKindOf(HTMLLanguage.INSTANCE) -> {
                return HtmlFileImpl(this) as HtmlFileImpl
            }

            language.isKindOf(CSSLanguage.INSTANCE) -> {
                return CssFileImpl(this) as CssFileImpl
            }

            language.isKindOf(NgxLanguage.INSTANCE) -> {
                return NgxFile(this)
            }

            language.isKindOf(TYPESCRIPT) -> {
                return parser?.createFile(this) as PsiFileImpl
            }

            else -> null
        }
    }


//    override fun createCopy(file: VirtualFile): NgxFileViewProvider {
//        return NgxFileViewProvider(manager, file, false)
//    }
//

//    override fun clone(): FileViewProvider {
//        Logger.warn("@@@@@@@@@@@@@ clone: ${virtualFile.name}")
//        return NgxFileViewProvider(manager, virtualFile, isEventSystemEnabled)
//
////        val originalContent = virtualFile.contentsToByteArray().toString(Charsets.UTF_8)
////        val copiedFile = LightVirtualFile(virtualFile.name, virtualFile.fileType, originalContent)
////        return NgxFileViewProvider(manager, copiedFile, false)
//    }


//    private fun createPsiFile(language: Language): PsiFile? {
//        Logger.warn("@@@@@@@@@@@@@ createPsiFile: ${language.id}")
//        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language)
//            ?: return null
//        return parserDefinition.createFile(this)
//    }
}
