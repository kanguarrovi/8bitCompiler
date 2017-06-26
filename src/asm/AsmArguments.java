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
import java.util.stream.Collectors;

public class AsmArguments implements AsmAst {

    public List<AsmAst> args = new ArrayList<>();

    public AsmArguments(List<AsmAst> args) {
        this.args = args;
    }

    @Override
    public AST getType() {
        return AST.ARGUMENTS;
    }

    @Override
    public void genCode(PrintStream out) {
        args.stream()
            .collect(Collectors.toCollection(LinkedList::new))
            .descendingIterator().forEachRemaining(X -> X.genCode(out));
    }
}
