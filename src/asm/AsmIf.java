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

public class AsmIf implements AsmAst {

    private AsmAst c, t, e;
    private AsmAst id;

    public AsmIf(AsmAst c, AsmAst t, AsmAst e, AsmAst i) {
        this.c  = c;
        this.t  = t;
        this.e  = e;
        this.id = i;
    }

    @Override
    public AST getType() {
        return AST.IF;
    }

    @Override
    public void genCode(PrintStream out) {
        c.genCode(out);
        out.print("\tPOP A\n");
        out.print("\tCMP A,0\n");
        out.print("\tJE ");
        id.genCode(out);
        out.print(e!=null? "_else\n": "_end\n");
        t.genCode(out);
        if(e!=null){
            out.print("\tJMP ");
            id.genCode(out);
            out.print("_end\n");
            id.genCode(out);
            out.print("_else:\n");
            e.genCode(out);
        }
        id.genCode(out);
        out.print("_end:\n"); 
    }
}
