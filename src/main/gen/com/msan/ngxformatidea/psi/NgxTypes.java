// This is a generated file. Not intended for manual editing.
package com.msan.ngxformatidea.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.msan.ngxformatidea.psi.impl.*;

public interface NgxTypes {

  IElementType BLOCK_COMMENT = new NgxElementType("BLOCK_COMMENT");
  IElementType COMMENT = new NgxElementType("COMMENT");
  IElementType COMPONENT = new NgxElementType("COMPONENT");
  IElementType COMPONENT_END = new NgxElementType("COMPONENT_END");
  IElementType COMPONENT_START = new NgxElementType("COMPONENT_START");
  IElementType CONTENT = new NgxElementType("CONTENT");
  IElementType LINE_COMMENT = new NgxElementType("LINE_COMMENT");
  IElementType TEXT = new NgxElementType("TEXT");
  IElementType WHITE_SPACE = new NgxElementType("WHITE_SPACE");


  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == BLOCK_COMMENT) {
        return new NgxBlockCommentImpl(node);
      }
      else if (type == COMMENT) {
        return new NgxCommentImpl(node);
      }
      else if (type == COMPONENT) {
        return new NgxComponentImpl(node);
      }
      else if (type == COMPONENT_END) {
        return new NgxComponentEndImpl(node);
      }
      else if (type == COMPONENT_START) {
        return new NgxComponentStartImpl(node);
      }
      else if (type == CONTENT) {
        return new NgxContentImpl(node);
      }
      else if (type == LINE_COMMENT) {
        return new NgxLineCommentImpl(node);
      }
      else if (type == TEXT) {
        return new NgxTextImpl(node);
      }
      else if (type == WHITE_SPACE) {
        return new NgxWhiteSpaceImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
