/* ----------------------------------------------------------------------
 * Universidad Nacional de Costa Rica
 * Facultad de Ciencias Exactas y Naturales
 * Escuela de Informática
 * EIF400 Paradigmas de Programación
 * Proyecto programado II
 * Profesor: Dr.Carlos Loría-Sáenz
 * Estudiantes:
 *	Carlos Arroyo Villalobos
 *	Jean Carlo Campos Madrigal
 *      Andrés Navarro Durán
 *	Karina Rivera Solano
 *	Claudio Umaña Arías
 * Octubre,2016
 * ----------------------------------------------------------------------
 * loriacarlos@gmail.com EIF400 II-2016
 * EightBit starting grammar
 * ----------------------------------------------------------------------*/
 
grammar EightBit;

// START
eightProgram       : eightFunction* eightMain* eightFunction*
;
////////////////////////////////////////////////////////////////////////
// FUN
eightFunction      : 'fun' id formals funBody 
;

formals            : '(' idList? ')'
;
idList             : id (',' id)* 
;
id                 : ID
;
funBody            :   letStatement       
                     | closedStatement 
                     | emptyStatement					 
;
eightMain: 'fun' 'main' '(' ')' funBody
;

////////////////////////////////////////////////////////////////////////
// STATEMENT
emptyStatement       : ';'
;
letStatement       : '{' 'let' '{'  assignStmtList? '}' closedStatement '}'
;
assignStmtList     :  assignStatement ';'? (assignStatement ';'?)*
;
closedStatement     : assignStatement  
                    | whileStatement  
					| ifStatement
					| forStatement
					| callStatement   
					| returnStatement 
					| blockStatement  
;
assignStatement         : id '=' expr
;
forStatement			: 'for' '(' assignStatement ';' expr ';' assignStatement ')' closedStatement
;
whileStatement          : 'while' '(' expr ')' closedStatement
;
ifStatement             : 'if' '(' expr ')' closedStatement ('else' closedStatement)?
;
callStatement           : ID arguments
;
returnStatement         : 'return' expr
;
blockStatement          : '{' closedList?  '}'
;
closedList              : closedStatement ';'? ( closedStatement ';'?)*
;
//////////////////////////////////////////////////////////////////////////////////
// EXPRESSION
expr            : relMonom ('||' relMonom)*
;
relMonom        : relOperation ('&&' relOperation)*
;

relOperation    : arithOperation ( relOperator arithOperation)*
                    | '!'  relOperation
;
relOperator     :	('>' | '<' | '==' | '<=' | '>=' | '!=')
;			
arithOperation  : arithMonom  operTDArithMonom*
;
arithMonom      : arithSingle operTDArithSingle*
;
arithSingle     :    
                     '(' expr ')'     #ArithParsSingle
				   | id arguments?    #ArithIdSingle
				   | constant         #ArithConstantSingle
;
operTDArithSingle : (oper = ('*' | '/')) arithSingle
;
operTDArithMonom :  (oper = ('+' | '-'))  arithMonom
;

constant        :    NUMBER  #ExprNum 
                   | STRING  #ExprString 
				   | 'true'  #ExprTrue
				   | 'false' #ExprFalse
				   | 'null'  #ExprNull
;

arguments : '(' args? ')'
;

args   :  expr (',' expr)*
;
///////////////////////////////////////////////////////////////////////
// LEXER

NUMBER : INTEGER ('.' INTEGER)? 
;
fragment INTEGER : [0-9]+ ;

STRING : ('"' (~'"')* '"' )
;

NOT : '!'
;
EQ : '=='
;
NEQ : '!='
;
LEQ : '<='
;
OR : '||'
;
TRUE : 'true'
;
FALSE : 'false'
;
MUL :   '*' 
; 
DIV :   '/' 
;
ADD :   '+' 
;
SUB :   '-' 
;
ID : [a-zA-Z][a-zA-Z_0-9]* 
;
////////////////////////////////////////////////
// Ignored tokens
SLC :   '/*'.*? '*/' -> skip
;
MLC : '//'.*? '\r'?'\n' -> skip
;         
WS  :   [ \t\r\n]+ -> skip
; 


