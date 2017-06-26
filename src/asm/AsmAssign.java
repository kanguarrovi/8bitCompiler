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

public class AsmAssign extends AsmOperation {

    final public static AsmId EQ = new AsmId("MOV ");

    public AsmAssign(AsmAst left, AsmAst right) {
        super(EQ, left, right);
    }

    @Override
    public AST getType() {
        return AST.ASSIGN;
    }

    @Override
    public void genCode(PrintStream out) {
        if (right.getType() == AST.OPERATION) {
            right.genCode(out);
            out.print("\tPOP A\n\t");
            oper.genCode(out);
            out.print("[");
            left.genCode(out);
            out.print("]");
            out.print(",A\n");
        } else {
            if (right.getType() == AST.ID) {
                out.print("\tMOV A,[");
                right.genCode(out);
                out.print("]\n");
            }
            out.print("\t");
            oper.genCode(out);
            out.print("[");
            left.genCode(out);
            out.print("], ");
            if (right.getType() == AST.ID) {
                out.print("A");
            } else {
                right.genCode(out);
            }
            out.print("\n");
        }
    }
}
