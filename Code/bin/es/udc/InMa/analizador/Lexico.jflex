/*
 * Analizador léxico 
 * Brais García Brenlla
 */

package es.udc.InMa.analizador;
import java_cup.runtime.Symbol; 

%% 
%class Lexer
%public 
%line 
%column
%char 
%cup 
%unicode
%ignorecase 

BLANCOS=[ \r\t\n]+
TEXTOBASICO=[^<]+
CAMPO=[a-zA-Z][^ \<\n\r\t\"/]*
CABECERA=xml (version=\"[0-9]+\.[0-9]\" encoding=\"[^ \?\r\t><]\")|(encoding=\"[^ \?\r\t><]\" version=\"[0-9]+\.[0-9]\")
%%

"<?"{CABECERA}"?>" {} 

"</"{CAMPO}\> {return new Symbol(sym.FINCAMPO, yytext().split("/")[1]);}
\<Autor\> {return new Symbol(sym.AUTOR, yytext().split("<")[1]);}
\<Titulo\> {return new Symbol(sym.TITULO,yytext().split("<")[1]);}
\<Texto\> {return new Symbol(sym.TEXTO, yytext().split("<")[1]);}
\<Imagen\> {return new Symbol(sym.IMAGEN,yytext().split("<")[1]);}
\<Video\> {return new Symbol(sym.VIDEO,yytext().split("<")[1]);}
\<Categoria\> {return new Symbol(sym.CATEGORIA,yytext().split("<")[1]);}
\<Fecha\> {return new Symbol(sym.FECHA, yytext().split("<")[1]);} 
\<Tipo\> {return new Symbol(sym.TIPO, yytext().split("<")[1]);} 
\<Color\> {return new Symbol(sym.COLOR, yytext().split("<")[1]);} 
\<Letra\> {return new Symbol(sym.LETRA, yytext().split("<")[1]);} 
\<ColorLetra\> {return new Symbol(sym.COLORLETRA, yytext().split("<")[1]);} 
\<Posicion\> {return new Symbol(sym.POSICION, yytext().split("<")[1]);} 
\<{CAMPO}\> {return new Symbol(sym.CAMPO, yytext().split("<")[1]);}

{BLANCOS} {}
{TEXTOBASICO} {return new Symbol(sym.TEXTOBASICO, yytext());}

. {
    System.out.println("Este es un error lexico: "+yytext()+
    ", en la linea: "+yyline+", en la columna: "+yychar);}