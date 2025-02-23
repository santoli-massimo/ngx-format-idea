// This is a generated file. Not intended for manual editing.
package com.msan.ngxformatidea.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.msan.ngxformatidea.psi.impl.*;

public interface NgxTypes {

  IElementType COMPONENT = new NgxElementType("COMPONENT");
  IElementType STYLE = new NgxElementType("STYLE");
  IElementType TEMPLATE = new NgxElementType("TEMPLATE");

  IElementType COMPONENT_BLOCK = new NgxTokenType("COMPONENT_BLOCK");
  IElementType STYLE_BLOCK = new NgxTokenType("STYLE_BLOCK");
  IElementType TEMPLATE_BLOCK = new NgxTokenType("TEMPLATE_BLOCK");
  IElementType WHITE_SPACE = new NgxTokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMPONENT) {
        return new NgxComponentImpl(node);
      }
      else if (type == STYLE) {
        return new NgxStyleImpl(node);
      }
      else if (type == TEMPLATE) {
        return new NgxTemplateImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
