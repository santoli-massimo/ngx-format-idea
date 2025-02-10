// This is a generated file. Not intended for manual editing.
package generated.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static generated.GeneratedTypes.*;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import generated.psi.*;

public class ComponentImpl extends GeneratedParserUtilBase implements Component {

  public ComponentImpl() {
    super();
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitComponent(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public Content getContent() {
    return findChildByClass(Content.class);
  }

}
