// This is a generated file. Not intended for manual editing.
package com.msan.ngxformatidea.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.msan.ngxformatidea.psi.impl.*;

public interface NgxTypes {

  IElementType COMPONENT = new NgxElementType("COMPONENT");

  IElementType COMMENT = new NgxTokenType("COMMENT");
  IElementType COMPONENT_END = new NgxTokenType("COMPONENT_END");
  IElementType COMPONENT_START = new NgxTokenType("COMPONENT_START");
  IElementType CONTENT = new NgxTokenType("CONTENT");
  IElementType WHITE_SPACE = new NgxTokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMPONENT) {
        return new NgxComponentImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
