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
 * ---------------------------------------------------------------------*/
package eightBit.asm;
import java.io.PrintStream;

public interface AsmAst {

    public static enum AST{
        ASMAST,PROGRAM,ARGS,ARGUMENTS,BLOCK,BOOLEAN,DATABLOCK,
        ASSIGN,OPERATION,ATOM,INTEGER,CMP,CALL,DATA,DEF,EMPTY,
        FORMALS,FUNCTION,ID,IF,LET,MAIN,POS,PRE,RELMON,RELOP,
        RETURN,STRING,WHILE,FOR
    };
    
    default AST getType() {
        return AST.ASMAST;
    }

    default void genCode() {
        genCode(System.out);
    }
    
    default void printParams(PrintStream out, String s){
        genCode(out);
    }
    

    public void genCode(PrintStream out);
}
