// This is a generated file. Not intended for manual editing.
package com.msan.ngxformatidea.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.msan.ngxformatidea.psi.NgxTypes.*;
import com.msan.ngxformatidea.psi.*;

public class NgxStyleImpl extends NgxComponentMixin implements NgxStyle {

  public NgxStyleImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull NgxVisitor visitor) {
    visitor.visitStyle(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof NgxVisitor) accept((NgxVisitor)visitor);
    else super.accept(visitor);
  }

}
