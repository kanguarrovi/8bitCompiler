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
import java.util.*;

public class AsmFormals implements AsmAst {

    List<AsmId> formals = new ArrayList<>();

    public AsmFormals(List<AsmId> value) {
        this.formals = value;
    }
    
    private void printFormal(PrintStream out, AsmAst x) {
        out.print("\tPOP B\n");
        out.print("\tMOV [");
        x.genCode(out);
        out.print("]");
        out.print(",B\n");
    }

    @Override
    public AST getType() {
        return AST.FORMALS;
    }

    @Override
    public void genCode(PrintStream out) {
        formals.stream().forEach(x -> printFormal(out, x));
    }

}
