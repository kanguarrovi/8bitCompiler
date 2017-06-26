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

public class AsmLet  implements AsmAst{
    List<AsmAst> assign = new ArrayList<>();
    AsmAst resto;

    public AsmLet(List<AsmAst> as, AsmAst res){
        assign = as;
        resto = res;
    }
    
    @Override
    public AST getType(){
        return AST.LET;
    }
    
    @Override
    public void genCode(PrintStream out) {
        assign.stream().forEach(X->X.genCode(out));
        resto.genCode(out);
    }
}
