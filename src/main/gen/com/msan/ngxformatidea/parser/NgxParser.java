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
  // COMPONENT_START CONTENT* COMPONENT_END
  public static boolean component(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "component")) return false;
    if (!nextTokenIs(builder_, COMPONENT_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMPONENT_START);
    result_ = result_ && component_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, COMPONENT_END);
    exit_section_(builder_, marker_, COMPONENT, result_);
    return result_;
  }

  // CONTENT*
  private static boolean component_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "component_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, CONTENT)) break;
      if (!empty_element_parsed_guard_(builder_, "component_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // component* COMMENT* WHITE_SPACE*
  static boolean file(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "file")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = file_0(builder_, level_ + 1);
    result_ = result_ && file_1(builder_, level_ + 1);
    result_ = result_ && file_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // component*
  private static boolean file_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "file_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!component(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "file_0", pos_)) break;
    }
    return true;
  }

  // COMMENT*
  private static boolean file_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "file_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, COMMENT)) break;
      if (!empty_element_parsed_guard_(builder_, "file_1", pos_)) break;
    }
    return true;
  }

  // WHITE_SPACE*
  private static boolean file_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "file_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, WHITE_SPACE)) break;
      if (!empty_element_parsed_guard_(builder_, "file_2", pos_)) break;
    }
    return true;
  }

}
