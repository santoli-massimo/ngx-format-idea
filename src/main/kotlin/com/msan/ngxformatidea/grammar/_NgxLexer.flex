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
WHITE_SPACE=\s+

TEXT=[^\[$\\\s]+
WHITE_SPACE=[^\S\n]+

%%
<YYINITIAL> {
  {WHITE_SPACE}             { return WHITE_SPACE; }

  "[component]"             { return COMPONENT_START; }
  "[/component]"            { return COMPONENT_END; }
  "//[^\\\\n]*"             { return LINE_COMMENT; }
  "/\\\\*[^*]*\\\\*/"       { return BLOCK_COMMENT; }

  {TEXT}                    { return TEXT; }
  {WHITE_SPACE}             { return WHITE_SPACE; }

}

[^] { return BAD_CHARACTER; }
