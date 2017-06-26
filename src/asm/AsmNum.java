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

public class AsmNum extends AsmAtom<Integer> {

    public AsmNum(Integer value) {
        super(value);
        ErrorControl.numValid(value);
    }
    @Override
    public void printParams(PrintStream out,String s){
        out.print("\t"+s+" ");
        genCode(out);
        out.print("\n");
    } 
    
    @Override
    public AST getType() {
        return AST.INTEGER;
    }
}
