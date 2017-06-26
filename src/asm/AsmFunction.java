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

public class AsmFunction implements AsmAst {

    AsmId name;
    AsmAst formals;
    AsmAst body;
    public boolean ret;

    public AsmFunction(AsmId na, AsmAst form, AsmAst bod, boolean rt) {
        this.name = na;
        this.formals = form;
        this.body = bod;
        this.ret = rt;
    }

    @Override
    public AST getType() {
        return AST.FUNCTION;
    }

    @Override
    public void genCode(PrintStream out) {
        name.genCode(out);
        out.print(":\n\tPOP C\n");
        out.print("\tMOV [.system_area], C\n");
        formals.genCode(out);
        body.genCode(out);
        
        if(!ret){ 
            out.print("\tPUSH 0\n");
            out.print("\tPUSH [.system_area]\n");
            out.print("\tRET\n");
        }
    }  
}
