package com.msan.ngxformatidea.language

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.msan.ngxformatidea.psi.NgxFile
import com.msan.ngxformatidea.utils.Logger
import com.intellij.openapi.util.Key;
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider


class NgxFileViewProvider(
    manager: PsiManager,
    file: VirtualFile,
    eventSystemEnabled: Boolean
//) : SingleRootFileViewProvider(manager, file, eventSystemEnabled), TemplateLanguageFileViewProvider {
//) : MultiplePsiFilesPerDocumentFileViewProvider(manager, file, eventSystemEnabled), TemplateLanguageFileViewProvider {
//) : MultiplePsiFilesPerDocumentFileViewProvider(manager, file, eventSystemEnabled), TemplateLanguageFileViewProvider {
//) : MultiplePsiFilesPerDocumentFileViewProvider(manager, file, eventSystemEnabled) {
//) : TemplateLanguageFileViewProvider(manager, file, eventSystemEnabled) {
) : SingleRootFileViewProvider(manager, file, eventSystemEnabled) {
    val CACHED_VIEW_PROVIDER: Key<NgxFileViewProvider> = Key<NgxFileViewProvider>("CACHED_VIEW_PROVIDER")

    init{
        virtualFile.putUserData(CACHED_VIEW_PROVIDER, this)
    }

    override fun getLanguages(): Set<Language> {
        return setOf(NgxLanguage.INSTANCE)
//        return setOf(NgxLanguage.INSTANCE, JavaScriptSupportLoader.TYPESCRIPT, HTMLLanguage.INSTANCE, CSSLanguage.INSTANCE )
    }

    override fun getBaseLanguage(): Language{
        return NgxLanguage.INSTANCE
//        return JavaScriptSupportLoader.TYPESCRIPT
    }

//    override fun createCopy(file: VirtualFile): NgxFileViewProvider {
//        return NgxFileViewProvider(manager, file, false)
//    }
//
//    override fun getTemplateDataLanguage(): Language = NgxLanguage.INSTANCE

//    override fun clone(): FileViewProvider {
//        Logger.warn("@@@@@@@@@@@@@ clone: ${virtualFile.name}")
//        return NgxFileViewProvider(manager, virtualFile, isEventSystemEnabled)
//
////        val originalContent = virtualFile.contentsToByteArray().toString(Charsets.UTF_8)
////        val copiedFile = LightVirtualFile(virtualFile.name, virtualFile.fileType, originalContent)
////        return NgxFileViewProvider(manager, copiedFile, false)
//    }

    // TYPESCRIPT

//    override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider {
//        Logger.warn("@@@@@@@@@@@@@ cloneInner: ${fileCopy.name}")
//        return NgxFileViewProvider(manager, fileCopy, isEventSystemEnabled)
//    }

//    override fun createFile(language: Language): PsiFile? {
//        return when {
//            // For your custom Ngx language:
//            language.isKindOf(NgxLanguage.INSTANCE) -> {
//                NgxFile(this)
//            }
//            else -> null
//        }
//
//        //        getKnownTreeRoots().forEach {
////            Logger.warn("@@@@@@@@@@@@@ getKnownTreeRoots: ${it.text}")
////        }
//    }

//    private fun createPsiFile(language: Language): PsiFile? {
//        Logger.warn("@@@@@@@@@@@@@ createPsiFile: ${language.id}")
//        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language)
//            ?: return null
//        return parserDefinition.createFile(this)
//    }
}
