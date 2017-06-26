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

public class AsmData implements AsmAst {
    AsmId name;
    AsmAst value;

    public AsmData(AsmId name, AsmAst value) {
        this.name = name;
        this.value = value;
    }

    public AsmId getName() {
        return name;
    }

    @Override
    public AST getType() {
        return AST.DATA;
    }

    @Override
    public void genCode(PrintStream out) {
        name.genCode(out);
        out.print(": DB ");
        value.genCode(out);
        out.print("\n");
        if (value.getType() == AST.STRING) {
            out.print("\tDB 0\n");
        }
    }
}
