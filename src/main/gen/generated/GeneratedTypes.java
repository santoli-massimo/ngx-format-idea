// This is a generated file. Not intended for manual editing.
package generated;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import generated.psi.impl.*;

public interface GeneratedTypes {

  IElementType COMPONENT = new IElementType("COMPONENT", null);
  IElementType CONTENT = new IElementType("CONTENT", null);

  IElementType TEXT = new IElementType("TEXT", null);

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMPONENT) {
        return new ComponentImpl(node);
      }
      else if (type == CONTENT) {
        return new ContentImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
