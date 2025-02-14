package com.msan.ngxformatidea.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.msan.ngxformatidea.psi.NgxTokenType;
import com.msan.ngxformatidea.psi.NgxTypes;
//import com.intellij.psi.xml.XmlTokenType;

%%

%{
  public NgxLexer() {
    this((java.io.Reader)null);
  }

  // This is requred fot the generated lexer to import all the tokens
//  public static final IElementType WHITE_SPACE = new TokenType.WHITE_SPACE("WHITE_SPACE");
//  public static final IElementType BAD_CHARACTER = new TokenType.BAD_CHARACTER("BAD_CHARACTER");

//  public static final IElementType COMPONENT_CONTENT = new NgxTokenType("COMPONENT_CONTENT");
//  public static final IElementType LINE_COMMENT = new NgxTokenType("LINE_COMMENT");
//  public static final IElementType BLOCK_COMMENT = new NgxTokenType("BLOCK_COMMENT");

//  public static final IElementType COMPONENT_START = new NgxTokenType("COMPONENT_START");
//  public static final IElementType COMPONENT_END = new NgxTokenType("COMPONENT_END");
//  public static final IElementType COMMENT = new NgxTokenType("COMMENT");

%}

%public
%class NgxLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

%state COMPONENT_MODE

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
//WHITE_SPACE_CHARS=[ \n\r\t\f\u2028\u2029\u0085]+
//
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

COMPONENT_START = "[component]"
COMPONENT_END = "[/component]"
CONTENT = "([^\[]|\[(?!/component\]))+"

%%

<YYINITIAL> {
  {COMPONENT_START}       { yybegin(COMPONENT_MODE); return NgxTypes.COMPONENT_START; }
}


<COMPONENT_MODE> {
  {COMPONENT_END}         { yybegin(YYINITIAL); return NgxTypes.COMPONENT_END; }
  {CONTENT}               { return NgxTypes.CONTENT; } // Treat everything inside as content
  .|\n                    { return NgxTypes.CONTENT; } // Treat everything inside as content
}

[^] { return TokenType.BAD_CHARACTER; }
