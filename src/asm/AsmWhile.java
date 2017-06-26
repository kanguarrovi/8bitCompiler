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

public class AsmWhile implements AsmAst {

    private AsmAst c, t;
    private AsmAst id;

    public AsmWhile(AsmAst c, AsmAst t, AsmAst i) {
        this.c  = c;
        this.t  = t;
        this.id = i;
    }

    @Override
    public AST getType() {
        return AST.WHILE;
    }

    @Override
    public void genCode(PrintStream out) {
        id.genCode(out); 
        out.print("_condition:\n");
        c.genCode(out);
        out.print("\tPOP A\n");
        out.print("\tCMP A,0\n");
        out.print("\tJE ");
        id.genCode(out);
        out.print("_end\n");
        t.genCode(out);
        out.print("\tJMP ");
        id.genCode(out);
        out.print("_condition\n");
        id.genCode(out);
        out.print("_end:\n");
    }
}
