/*
  Created By: Fei Song
  File Name: tiny.flex
  To Build: jflex tiny.flex

  and then after the parser is created
    javac Lexer.java
*/
   
/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column
    
/* 
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup
   
/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
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
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */


"else"             {  return symbol(sym.ELSE); }
"if"               {  return symbol(sym.IF); }
"int"              {  return symbol(sym.INT); }
"return"           {  return symbol(sym.RETURN); }
"void"             {  return symbol(sym.VOID); }
"while"            {  return symbol(sym.WHILE); }

"+"                {  return symbol(sym.PLUS); }
"-"                {  return symbol(sym.MINUS); }
"*"                {  return symbol(sym.TIMES); }
"/"                {  return symbol(sym.OVER); }

"<"                { return symbol(sym.LT); }
"<="               { return symbol(sym.LTEQ); }
">"                { return symbol(sym.GT); }
">="               { return symbol(sym.GTEQ); }
"=="               { return symbol(sym.EQ); }
"!="               { return symbol(sym.NOTEQ); }

"="                { return symbol(sym.ASSIGN); }
";"                { return symbol(sym.SEMI); }
","                { return symbol(sym.COMMA); }


"("                { return symbol(sym.LPAREN); }
")"                { return symbol(sym.RPAREN); }

"["                { return symbol(sym.LSQUARE); }
"]"                { return symbol(sym.RSQUARE); }


"{"                { return symbol(sym.LCURL); }
"}"                { return symbol(sym.RCURL); }

{identifier}       { return symbol(sym.ID, yytext()); }
{number}           { return symbol(sym.NUM, yytext()); }


{WhiteSpace}*      { /* skip whitespace */ }   
"/*"[^\}]*"*/"     { /* skip comments */ }

.                  { return symbol(sym.ERROR); }

/************************Old Definitions***********************************/
 
"if"               { return symbol(sym.IF); }
"then"             { return symbol(sym.THEN); }
"else"             { return symbol(sym.ELSE); }
"end"              { return symbol(sym.END); }
"repeat"           { return symbol(sym.REPEAT); }
"until"            { return symbol(sym.UNTIL); }
"read"             { return symbol(sym.READ); }
"write"            { return symbol(sym.WRITE); }
":="               { return symbol(sym.ASSIGN); }
"="                { return symbol(sym.EQ); }
"<"                { return symbol(sym.LT); }
">"                { return symbol(sym.GT); }
"+"                { return symbol(sym.PLUS); }
"-"                { return symbol(sym.MINUS); }
"*"                { return symbol(sym.TIMES); }
"/"                { return symbol(sym.OVER); }
"("                { return symbol(sym.LPAREN); }
")"                { return symbol(sym.RPAREN); }
";"                { return symbol(sym.SEMI); }
{number}           { return symbol(sym.NUM, yytext()); }
{identifier}       { return symbol(sym.ID, yytext()); }
{WhiteSpace}*      { /* skip whitespace */ }   
"{"[^\}]*"}"       { /* skip comments */ }
.                  { return symbol(sym.ERROR); }

/************************************************
1st Half - Stephen

1) else
2) if
3) int
4) return
5) void 
6) while
7) +
9) -
10) *
11) /
12) <
13) <=
14) >
15) >=
16) ==
17) !=

2nd Half - Bashir

18) =
19) ;
20) ,
21) (
22) )
23) [
24) ]
25) {
26) }
27) /*
28) */
29) ID
30) NUM
31) letter
32) digit
33) whitespace (blanks,newlines,tabs)

************************************************/