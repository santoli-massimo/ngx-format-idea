package com.msan.ngxformatidea.psi.impl

import com.msan.ngxformatidea.psi.NgxTypes
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement


object NgxPsiImplUtil {
    fun createElement(node: ASTNode): PsiElement {
        return when (node.elementType) {
            NgxTypes.COMPONENT_START -> NgxComponentImpl(node)
            else -> throw IllegalArgumentException("Unknown element type: ${node.elementType}")
        }
    }
}
