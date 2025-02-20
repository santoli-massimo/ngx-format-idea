package com.msan.ngxformatidea.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.msan.ngxformatidea.psi.NgxTokenType;
import com.msan.ngxformatidea.psi.NgxTypes;
%%

%{
  public NgxLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class NgxLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

WHITE_SPACE = [ \n\r\t\f\u2028\u2029\u0085]+

%state TEMPLATE_MODE
template_key = "template"
TEMPLATE_START = "[template]"
TEMPLATE_END = "[/template]"
TEMPLATE_CONTENT = "([^\[]|\[(?!\/?template\]))+"

%state STYLE_MODE
style_key = "style"
STYLE_START = "[style]"
STYLE_END = "[/style]"
STYLE_CONTENT = "([^\[]|\[(?!\/?style\]))+"

%state COMPONENT_MODE
component_key = "component"
COMPONENT_START = "[component]"
COMPONENT_END = "[/component]"
COMPONENT_CONTENT = "([^\[]|\[(?!\/?component\]))+"


%%

<YYINITIAL> {
  {WHITE_SPACE}       { return NgxTypes.WHITE_SPACE; }
  {TEMPLATE_START}    { yybegin(TEMPLATE_MODE); return NgxTypes.TEMPLATE_START; }
  {STYLE_START}       { yybegin(STYLE_MODE); return NgxTypes.STYLE_START; }
  {COMPONENT_START}   { yybegin(COMPONENT_MODE); return NgxTypes.COMPONENT_START; }
}

<TEMPLATE_MODE> {
  {TEMPLATE_END}      { yybegin(YYINITIAL); return NgxTypes.TEMPLATE_END; }
  .|\n                { return NgxTypes.TEMPLATE_CONTENT; }
}

<STYLE_MODE> {
  {STYLE_END}         { yybegin(YYINITIAL); return NgxTypes.STYLE_END; }
  .|\n                { return NgxTypes.STYLE_CONTENT; }
}

<COMPONENT_MODE> {
  {COMPONENT_END}     { yybegin(YYINITIAL); return NgxTypes.COMPONENT_END; }
  .|\n                { return NgxTypes.COMPONENT_CONTENT; }
}

.|\n { return TokenType.BAD_CHARACTER; }  // Catch-all for unexpected tokens





//WHITE_SPACE="[ \t\f\r\n]+"
//LINE_COMMENT="//" [^\n]*
//BLOCK_COMMENT="/*" .* "*/"

//COMPONENT_CONTENT = TokenType.CODE_FRAGMENT
//COMPONENT_CONTENT = [^\[]+(\[[^\]]+\])?[^\]]*
//COMPONENT_CONTENT = ([^\[]|\[(?!/component\])]+)*
//COMPONENT_CONTENT = ([^\\[] | \\[(?!/component\\])[^\\]]*)+
//COMPONENT_CONTENT = ([^\[] | \[[^/])*
//COMPONENT_CONTENT =  ([^\[] | \[[^\]/])+
//COMPONENT_CONTENT = ([^\[] | \[([^\/] | \/[^c] | \/c[^o] | \/co[^m] | \/com[^p] | \/comp[^o] | \/compo[^n] | \/compon[^e] | \/componen[^t] | \/component[^\]]) )*
//COMPONENT_CONTENT =  "([^\[] | \[(?!\/component\]) [^\]]+ )+"
//COMPONENT_CONTENT =  "([^\[] | \[(?!\/component\]) [^\]]*)*"
//COMPONENT_CONTENT =  "([^\[] | \[(?!\/component\]) [^\]]*)*"
//COMPONENT_CONTENT =  "([^\[] | \[(?!\/component\]) . )*"
//COMPONENT_CONTENT =  "([^\[\]] | \[(?!\/component\]) [^\]]*\])*"

//COMPONENT_CONTENT = .|\n

//ALPHA=[:letter:]
//DIGIT=[0-9]

//TAG_NAME=({ALPHA}|"_"|":")({ALPHA}|{DIGIT}|"_"|":"|"."|"-")*
//ATTRIBUTE_NAME=([^ \n\r\t\f\"\'<>/=])+
//
//
//LineTerminator = \r|\n|\r\n
//InputCharacter = [^\r\n]
//WhiteSpace     = {LineTerminator} | [ \t\f]
//
//TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
//EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
//DocumentationComment = "/**" {CommentContent} "*"+ "/"
//CommentContent       = ( [^*] | \*+ [^/*] )*
//
//COMMENT = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

//CONTENT = "([^\[]|\[(?!/component\]))+"