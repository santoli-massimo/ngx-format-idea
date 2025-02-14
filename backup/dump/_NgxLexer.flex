package com.msan.ngxformatidea.grammar;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.msan.ngxformatidea.psi.NgxTypes.*;

%%

%{
  public _NgxLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _NgxLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
//WHITE_SPACE=\s+

//TEXT=[^\[$\\\s]+

//TEXT=[^\[\]\n\r]+
//WHITE_SPACE=[ \n\r\t]+
//COMPONENT_START=\[component\]
//COMPONENT_END=\[\/component\]
//LINE_COMMENT=[^\\n]*
//BLOCK_COMMENT=\*[^*]*\*\/

//TEXT=[^\[\]\n\r]+

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

/* comments */
LINE_COMMENT = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
WHITE_SPACE="[ \n\r\t]+"
//TEXT="[^\[\]\n\r]+"
TEXT="[^\[$\\\s]+"
COMPONENT_START="\[component\]"
COMPONENT_END="\[\/component\]"
//COMPONENT = "[component]" | "regexp:(.*)" | "[/component]"

%%
<YYINITIAL> {
  {WHITE_SPACE}             { return WHITE_SPACE; }

  {COMPONENT_START}         { return COMPONENT_START; }
  {COMPONENT_END}           { return COMPONENT_END; }
  {LINE_COMMENT}            { return LINE_COMMENT; }

  {TEXT}                    { return TEXT; }
}

[^] { return BAD_CHARACTER; }
