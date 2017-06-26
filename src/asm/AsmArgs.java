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

public class AsmArgs implements AsmAst {

    AsmAst valor;

    public AsmAst getValor() {
        return valor;
    }

    public AsmArgs(AsmAst valor) {
        String name;
        if (valor instanceof AsmId) {
            name = ((AsmId) valor).getValue();
            if (ControlAreas.getTablaS(name) == null) {
                ControlAreas.putTablaS(name, this);
                this.valor = valor;
            } else {
                this.valor = ControlAreas.getTablaS(name);
            }
        } else {
            this.valor = valor;
        }
    }
    
    @Override
    public AST getType() {
        return AST.ARGS;
    }

    @Override
    public void genCode(PrintStream out) {
            
         valor.printParams(out, "PUSH");
         
    }

}
