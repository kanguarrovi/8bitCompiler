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

public class AsmFor implements AsmAst {

    private AsmAst i, c, m, b;
    private AsmAst id;

    public AsmFor(AsmAst inst, AsmAst condition, AsmAst mod, AsmAst body, AsmAst id) {
        this.i  = inst;
        this.c  = condition;
        this.m  = mod;
        this.b  = body;
        this.id = id;
    }

    @Override
    public AST getType() {
        return AST.FOR;
    }

    @Override
    public void genCode(PrintStream out){     
        i.genCode(out);
        id.genCode(out); 
        out.print("_for_condition:\n");
        c.genCode(out);
        out.print("\tPOP A\n");
        out.print("\tCMP A,0\n");
        out.print("\tJE ");
        id.genCode(out);
        out.print("_for_end\n");
        b.genCode(out);
        m.genCode(out);
        out.print("\tJMP ");
        id.genCode(out);
        out.print("_for_condition\n");
        id.genCode(out);
        out.print("_for_end:\n");
    }
}
