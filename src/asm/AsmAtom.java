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

public class AsmAtom<T> implements AsmAst {

    private T value;

    public AsmAtom(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    public AST getType() {
        return AST.ATOM;
    }

    @Override
    public void genCode(PrintStream out) {
        out.print(this.value);
    }
}
