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
import java.util.*;
import java.io.PrintStream;

public class AsmBlock implements AsmAst {

    protected List<AsmAst> members;

    public AsmBlock(List<AsmAst> members) {
        this.members = members;
    }

    @Override
    public AST getType() {
        return AST.BLOCK;
    }

    @Override
    public void genCode(PrintStream out) {
        members.stream().forEach(m -> m.genCode(out));
    }

}
