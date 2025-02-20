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
  IElementType TS_ELEMENT = new NgxElementType("TS_ELEMENT");

  IElementType COMPONENT_CONTENT = new NgxTokenType("COMPONENT_CONTENT");
  IElementType COMPONENT_END = new NgxTokenType("COMPONENT_END");
  IElementType COMPONENT_START = new NgxTokenType("COMPONENT_START");
  IElementType STYLE_CONTENT = new NgxTokenType("STYLE_CONTENT");
  IElementType STYLE_END = new NgxTokenType("STYLE_END");
  IElementType STYLE_START = new NgxTokenType("STYLE_START");
  IElementType TEMPLATE_CONTENT = new NgxTokenType("TEMPLATE_CONTENT");
  IElementType TEMPLATE_END = new NgxTokenType("TEMPLATE_END");
  IElementType TEMPLATE_START = new NgxTokenType("TEMPLATE_START");
  IElementType TS_EXPRESSION = new NgxTokenType("TS_EXPRESSION");
  IElementType TS_STATEMENT = new NgxTokenType("TS_STATEMENT");
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
      else if (type == TS_ELEMENT) {
        return new NgxTsElementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
