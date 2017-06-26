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

public class AsmRelMonom implements AsmAst {

    List<AsmAst> operations = new ArrayList<>();

    public AsmRelMonom(List<AsmAst> l) {
        operations = l;
    }

    public void add(AsmAst a) {
        operations.add(a);
    }

    public void pCMPs(PrintStream out, int x) {
        out.print("\tPOP B\n");
        out.print("\tPOP A\n");
        out.print("\tOR A, B\n");
        out.print("\tPUSH A\n");
    }

    @Override
    public AST getType() {
        return AST.RELMON;
    }
    
    @Override
    public void genCode(PrintStream out) {
        if (operations.size() == 1) {
            operations.get(0).genCode(out);
        } else {
            operations.stream().forEach(x -> x.printParams(out,"PUSH"));
            IntStream.range(1, operations.size()).forEach(x -> pCMPs(out, x));
        }
    }
}
