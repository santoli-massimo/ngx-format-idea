// This is a generated file. Not intended for manual editing.
package com.example.ngx.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static generated.GeneratedTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class NgxParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return file(b, l + 1);
  }

  /* ********************************************************** */
  // "[component]" content? "[/component]"
  public static boolean component(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "component")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMPONENT, "<component>");
    r = consumeToken(b, "[component]");
    r = r && component_1(b, l + 1);
    r = r && consumeToken(b, "[/component]");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // content?
  private static boolean component_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "component_1")) return false;
    content(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TEXT
  public static boolean content(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "content")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT);
    exit_section_(b, m, CONTENT, r);
    return r;
  }

  /* ********************************************************** */
  // component*
  static boolean file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "file")) return false;
    while (true) {
      int c = current_position_(b);
      if (!component(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "file", c)) break;
    }
    return true;
  }

}
