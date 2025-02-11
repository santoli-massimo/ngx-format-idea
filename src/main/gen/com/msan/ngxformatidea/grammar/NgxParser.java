// This is a generated file. Not intended for manual editing.
package com.msan.ngxformatidea.grammar;

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
  // "/\\*([^*]|\\*+[^*/])*\\*+/"
  public static boolean BLOCK_COMMENT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "BLOCK_COMMENT")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BLOCK_COMMENT, "<block comment>");
    result_ = consumeToken(builder_, "/\\*([^*]|\\*+[^*/])*\\*+/");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "[/component]"
  public static boolean COMPONENT_END(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "COMPONENT_END")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPONENT_END, "<component end>");
    result_ = consumeToken(builder_, "[/component]");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "[component]"
  public static boolean COMPONENT_START(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "COMPONENT_START")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPONENT_START, "<component start>");
    result_ = consumeToken(builder_, "[component]");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "//[^\\n]*"
  public static boolean LINE_COMMENT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "LINE_COMMENT")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, LINE_COMMENT, "<line comment>");
    result_ = consumeToken(builder_, "//[^\\n]*");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "[^\\[\\]\\n\\r]+"
  public static boolean TEXT(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "TEXT")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, TEXT, "<text>");
    result_ = consumeToken(builder_, "[^\\[\\]\\n\\r]+");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "\\s+"
  public static boolean WHITE_SPACE(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "WHITE_SPACE")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, WHITE_SPACE, "<white space>");
    result_ = consumeToken(builder_, "\\s+");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // LINE_COMMENT | BLOCK_COMMENT
  public static boolean comment(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comment")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMENT, "<comment>");
    result_ = LINE_COMMENT(builder_, level_ + 1);
    if (!result_) result_ = BLOCK_COMMENT(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // COMPONENT_START content? COMPONENT_END
  public static boolean component(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "component")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMPONENT, "<component>");
    result_ = COMPONENT_START(builder_, level_ + 1);
    result_ = result_ && component_1(builder_, level_ + 1);
    result_ = result_ && COMPONENT_END(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // content?
  private static boolean component_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "component_1")) return false;
    content(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // TEXT+
  public static boolean content(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "content")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CONTENT, "<content>");
    result_ = TEXT(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!TEXT(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "content", pos_)) break;
    }
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // component
  static boolean file(PsiBuilder builder_, int level_) {
    return component(builder_, level_ + 1);
  }

}
