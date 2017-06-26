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

public class AsmOperation implements AsmAst {

    protected AsmAst oper;
    protected AsmAst left, right;

    public AsmOperation(AsmAst oper, AsmAst left, AsmAst right) {
        this.oper = oper;
        this.left = left;
        this.right = right;
    }

    public AsmAst getOper() {
        return this.oper;
    }

    public AsmAst getLeft() {
        return this.left;
    }

    public AsmAst getRight() {
        return this.right;
    }
    
    @Override
    public AST getType() {
        return AST.OPERATION;
    }

//    private void printPUSH(AsmAst aux, PrintStream out) {
//        if (aux.getType() == AST.CALL || aux.getType() == AST.POS || aux.getType() == AST.PRE) {
//            aux.genCode(out);
//        } else {
//            out.print("\tPUSH ");
//            if (aux.getType() == AST.ID) {
//                out.print("[");
//                aux.genCode(out);
//                out.print("]\n");
//            } else {
//                aux.genCode(out);
//                out.print("\n");
//            }
//        }
//    }

    private void printOPER(AsmAst aux, PrintStream out) {

        out.print("\tPOP B\n");
        out.print("\tPOP A\n\t");
        oper.genCode(out);
        if (((AsmId) aux).getValue().equals("MUL ") || ((AsmId) aux).getValue().equals("DIV ")) {
            out.print("B\n");
        } else {
            out.print("A,B\n");
        }
        out.print("\tPUSH A\n");
    }

    @Override
    public void genCode(PrintStream out) {
        left.printParams(out, "PUSH");
        
        right.printParams(out, "PUSH");
        
        printOPER(oper, out);
    }
}
