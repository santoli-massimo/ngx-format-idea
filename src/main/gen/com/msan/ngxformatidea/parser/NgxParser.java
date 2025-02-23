// This is a generated file. Not intended for manual editing.
package com.msan.ngxformatidea.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.msan.ngxformatidea.psi.NgxTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class NgxParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return file(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // COMPONENT_BLOCK
  public static boolean component(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "component")) return false;
    if (!nextTokenIs(builder_, COMPONENT_BLOCK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMPONENT_BLOCK);
    exit_section_(builder_, marker_, COMPONENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // (template | style | component | WHITE_SPACE)*
  static boolean file(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "file")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!file_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "file", pos_)) break;
    }
    return true;
  }

  // template | style | component | WHITE_SPACE
  private static boolean file_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "file_0")) return false;
    boolean result_;
    result_ = template(builder_, level_ + 1);
    if (!result_) result_ = style(builder_, level_ + 1);
    if (!result_) result_ = component(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, WHITE_SPACE);
    return result_;
  }

  /* ********************************************************** */
  // STYLE_BLOCK
  public static boolean style(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "style")) return false;
    if (!nextTokenIs(builder_, STYLE_BLOCK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STYLE_BLOCK);
    exit_section_(builder_, marker_, STYLE, result_);
    return result_;
  }

  /* ********************************************************** */
  // TEMPLATE_BLOCK
  public static boolean template(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template")) return false;
    if (!nextTokenIs(builder_, TEMPLATE_BLOCK)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, TEMPLATE_BLOCK);
    exit_section_(builder_, marker_, TEMPLATE, result_);
    return result_;
  }

}
