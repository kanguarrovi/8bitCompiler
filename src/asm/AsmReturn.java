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

public class AsmReturn implements AsmAst {

    private AsmAst e;

    public AsmReturn(AsmAst e) {
        this.e = e;
    }

    @Override
    public AST getType() {
        return AST.RETURN;
    }

    @Override
    public void genCode(PrintStream out) {
        e.printParams(out, "PUSH");
        out.print("\tPUSH [.system_area]\n");
        out.print("\tRET\n");
    }
}
