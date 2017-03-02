/*
  File Name: tiny.flex
  JFlex specification for the TINY language
*/
   
%%
   
%class Lexer
%type Token
%line
%column
    
%eofval{
  return null;
%eofval};

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]
   
/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
digit = [0-9]
number = {digit}+
   
/* A identifier integer is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
letter = [a-zA-Z]
identifier = {letter}+
   
%%
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */

"else"             { return new Token(Token.ELSE, yytext(), yyline, yycolumn); }
"if"               { return new Token(Token.IF, yytext(), yyline, yycolumn); }
"int"              { return new Token(Token.INT, yytext(), yyline, yycolumn); }
"return"           { return new Token(Token.RETURN, yytext(), yyline, yycolumn); }
"void"             { return new Token(Token.VOID, yytext(), yyline, yycolumn); }
"while"            { return new Token(Token.WHILE, yytext(), yyline, yycolumn); }

"+"                { return new Token(Token.PLUS, yytext(), yyline, yycolumn); }
"-"                { return new Token(Token.MINUS, yytext(), yyline, yycolumn); }
"*"                { return new Token(Token.TIMES, yytext(), yyline, yycolumn); }
"/"                { return new Token(Token.OVER, yytext(), yyline, yycolumn); }

"<"                { return new Token(Token.LT, yytext(), yyline, yycolumn); }
"<="               { return new Token(Token.LTEQ, yytext(), yyline, yycolumn); }
">"                { return new Token(Token.GT, yytext(), yyline, yycolumn); }
">="               { return new Token(Token.GTEQ, yytext(), yyline, yycolumn); }
"="                { return new Token(Token.EQ, yytext(), yyline, yycolumn); }
"!="               { return new Token(Token.NOTEQ, yytext(), yyline, yycolumn); }

":="               { return new Token(Token.ASSIGN, yytext(), yyline, yycolumn); }
";"                { return new Token(Token.SEMI, yytext(), yyline, yycolumn); }
",="               { return new Token(Token.COMMA, yytext(), yyline, yycolumn); }


"("                { return new Token(Token.LPAREN, yytext(), yyline, yycolumn); }
")"                { return new Token(Token.RPAREN, yytext(), yyline, yycolumn); }

"["                { return new Token(Token.LSQUARE, yytext(), yyline, yycolumn); }
"]"                { return new Token(Token.RSQUARE, yytext(), yyline, yycolumn); }


"{"                { return new Token(Token.LCURL, yytext(), yyline, yycolumn); }
"}"                { return new Token(Token.RCURL, yytext(), yyline, yycolumn); }

{identifier}       { return new Token(Token.ID, yytext(), yyline, yycolumn); }
{number}           { return new Token(Token.NUM, yytext(), yyline, yycolumn); }


{WhiteSpace}*      { /* skip whitespace */ }   
"{"[^\}]*"}"       { /* skip comments */ }

.                  { return new Token(Token.ERROR, yytext(), yyline, yycolumn); }
