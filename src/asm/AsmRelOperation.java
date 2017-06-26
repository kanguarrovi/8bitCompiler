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
import java.util.stream.IntStream;

public class AsmRelOperation implements AsmAst {

    List<AsmAst> operations = new ArrayList<>();
    AsmAst not;

    public AsmRelOperation(List<AsmAst> l, AsmAst n) {
        operations = l;
        not = n;
    }

    public AsmRelOperation add(AsmAst a) {
        operations.add(a);
        return this;
    }

    public void pCMPs(PrintStream out, int x) {
        out.print("\tPOP B\n");
        out.print("\tPOP A\n");
        out.print("\tAND A, B\n");
        out.print("\tPUSH A\n");
    }

    public void printNEG(PrintStream out, AsmAst a) {

        a.printParams(out,"PUSH");
        
        if (a.getType() == AST.RELOP && ((AsmRelOperation) a).not != null) {
            out.print("\tPOP A\n");
            out.print("\tNOT A\n");
            out.print("\tPUSH A\n");
        }
    }

    @Override
    public AST getType() {
        return AST.RELOP;
    }

    @Override
    public void genCode(PrintStream out) {
        operations.stream().forEach(x -> printNEG(out, x));
        IntStream.range(1, operations.size()).forEach(x -> pCMPs(out, x));
    }
}
