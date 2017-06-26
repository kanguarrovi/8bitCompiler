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

import java.io.*;

public class AsmCMP implements AsmAst {

    protected AsmAst oper;
    protected AsmAst left, right;
    protected AsmAst id;
    
    public AsmCMP(AsmAst oper, AsmAst left, AsmAst right, AsmAst funId) {
        this.oper   = oper;
        this.left   = left;
        this.right  = right;
        this.id     = funId;
    }

    @Override
    public AST getType() {
        return AST.CMP;
    }
    
    
    
    
  
    @Override
    public void genCode(PrintStream out) {
        
        left.printParams(out, "MOV A,");

        right.printParams(out,"MOV B,");
        
        if (right.getType()!=AST.ID && right.getType()!=AST.INTEGER) 
            out.print("\tPOP B\n");
        
        if (left.getType()!=AST.ID && left.getType()!=AST.INTEGER) 
            out.print("\tPOP A\n");      
  
        out.print("\tCMP A, B\n\t");  
        oper.genCode(out);
        id.genCode(out);
        out.print("\n");
        out.print("\tPUSH 0\n");
        out.print("\tJMP ");
        id.genCode(out);
        out.print("_end\n");
        id.genCode(out);
        out.print(":\n");
        out.print("\tPUSH 1\n");
        id.genCode(out);
        out.print("_end:\n");
    }
    
}
