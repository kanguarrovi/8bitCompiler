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

public class AsmMain implements AsmAst {

    protected AsmAst body;

    public AsmMain(AsmAst body) {
        this.body = body;
    }

    @Override
    public AST getType() {
        return AST.MAIN;
    }

    @Override
    public void genCode(PrintStream out) {
        out.print(".main:\n");
        body.genCode(out);
        out.print("\tHLT\n");
    }

}
