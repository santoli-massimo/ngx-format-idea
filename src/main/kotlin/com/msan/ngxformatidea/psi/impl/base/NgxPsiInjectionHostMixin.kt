package com.msan.ngxformatidea.psi.impl.base

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.openapi.util.TextRange
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiLanguageInjectionHost
import com.msan.ngxformatidea.psi.NgxFile
import com.msan.ngxformatidea.psi.NgxFileType


abstract class NgxPsiInjectionHostMixin(node: ASTNode) : ASTWrapperPsiElement(node), PsiLanguageInjectionHost {
    abstract val injectionLanguage: Language
    abstract val fileName: String
    abstract val startMarker: String
    abstract val endMarker: String
    abstract val implClass: Class<out PsiElement>

    override fun isValidHost(): Boolean = true

    override fun updateText(text: String): PsiLanguageInjectionHost {
        // @TODO: not always working for typescript, EG: create getter and setter does nothing
        val valueNode = node.firstChildNode
        val file = PsiFileFactory.getInstance(project).createFileFromText(fileName, NgxFileType.INSTANCE, text) as NgxFile
        val newNode = file.findChildByClass(implClass) ?: return this
        node.replaceChild(valueNode, newNode.node)

        return this
    }

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> {
        return object : LiteralTextEscaper<NgxPsiInjectionHostMixin>(this) {
            override fun decode(range: TextRange, outChars: StringBuilder): Boolean {
                outChars.append(range.substring(myHost.text))
                return true
            }

            override fun getOffsetInHost(offsetInDecoded: Int, range: TextRange): Int {
                val offset = offsetInDecoded + range.startOffset
                return if (offset <= range.endOffset) offset else -1
            }

            override fun isOneLine(): Boolean = false
        }
    }

    fun updateContent(content: String) {
        val contentWithMarkers = addMarkers(content)
        updateText(contentWithMarkers)
    }

    fun content(): String{
        return this.text.replace(startMarker, "").replace(endMarker, "").trim()
    }

    fun addMarkers(content: String): String {
        return """
            |${startMarker}
            |${content}
            |${endMarker}
        """.trimMargin()

//        return """
//            |${startMarker}
//            |${content.trim().prependIndent("\t")}}
//            |${endMarker}
//        """.trimMargin()
    }
}
