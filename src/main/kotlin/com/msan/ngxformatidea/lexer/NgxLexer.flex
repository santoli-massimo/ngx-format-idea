package com.msan.ngxformatidea.lexer;


import com.intellij.psi.tree.IElementType;
import static com.example.ngx.psi.NgxTypes.*;

%%

%class NgxLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

%%

"[component]"     { return COMPONENT_START; }
"[/component]"    { return COMPONENT_END; }
[^]               { return TEXT; }