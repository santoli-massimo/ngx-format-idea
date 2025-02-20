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
  // COMPONENT_START (COMPONENT_CONTENT)* COMPONENT_END
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

  // (COMPONENT_CONTENT)*
  private static boolean component_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "component_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, COMPONENT_CONTENT)) break;
      if (!empty_element_parsed_guard_(builder_, "component_1", pos_)) break;
    }
    return true;
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
  // STYLE_START (STYLE_CONTENT)* STYLE_END
  public static boolean style(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "style")) return false;
    if (!nextTokenIs(builder_, STYLE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STYLE_START);
    result_ = result_ && style_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, STYLE_END);
    exit_section_(builder_, marker_, STYLE, result_);
    return result_;
  }

  // (STYLE_CONTENT)*
  private static boolean style_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "style_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, STYLE_CONTENT)) break;
      if (!empty_element_parsed_guard_(builder_, "style_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // TEMPLATE_START (TEMPLATE_CONTENT)* TEMPLATE_END
  public static boolean template(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template")) return false;
    if (!nextTokenIs(builder_, TEMPLATE_START)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, TEMPLATE_START);
    result_ = result_ && template_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, TEMPLATE_END);
    exit_section_(builder_, marker_, TEMPLATE, result_);
    return result_;
  }

  // (TEMPLATE_CONTENT)*
  private static boolean template_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "template_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, TEMPLATE_CONTENT)) break;
      if (!empty_element_parsed_guard_(builder_, "template_1", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // TS_EXPRESSION | TS_STATEMENT
  public static boolean ts_element(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "ts_element")) return false;
    if (!nextTokenIs(builder_, "<ts element>", TS_EXPRESSION, TS_STATEMENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TS_ELEMENT, "<ts element>");
    result_ = consumeToken(builder_, TS_EXPRESSION);
    if (!result_) result_ = consumeToken(builder_, TS_STATEMENT);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

}
