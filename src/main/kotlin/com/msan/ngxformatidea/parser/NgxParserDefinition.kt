package com.msan.ngxformatidea.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.msan.ngxformatidea.language.NgxLanguage
import com.msan.ngxformatidea.lexer.NgxLexerAdapter
import com.msan.ngxformatidea.psi.NgxFile
import com.msan.ngxformatidea.psi.NgxTypes
import com.msan.ngxformatidea.psi.NgxTypes.COMPONENT
import com.msan.ngxformatidea.psi.impl.NgxComponentImpl


internal class NgxParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer {
        return NgxLexerAdapter()
    }

    override fun createParser(project: Project?): PsiParser {
        return NgxParser()
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.create(NgxTypes.COMMENT)
    }

    override fun getWhitespaceTokens(): TokenSet {
        return TokenSet.WHITE_SPACE
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.ANY
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return NgxFile(viewProvider)
    }


    override fun createElement(node: ASTNode): PsiElement {
        return when (node.elementType) {
            COMPONENT -> NgxComponentImpl(node)
            else -> throw AssertionError("Unknown element type: ${node.elementType}")
        }
    }

    companion object {
        val FILE: IFileElementType = IFileElementType(NgxLanguage.INSTANCE)
    }
}