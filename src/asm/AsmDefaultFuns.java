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

public class AsmDefaultFuns implements AsmAst {

    public boolean str;
    public boolean num;
    public boolean bool;
    public boolean cls;

    @Override
    public AST getType() {
        return AST.DEF;
    }

    @Override
    public void genCode(PrintStream out) {
        if(str){
            printString(out);
        }
        if(num){
            printNumber(out);
        }
        if(bool){
            if(!str) printString(out);
            printBool(out);
        }
        if(cls){
            printClrScr(out);
        }
    }
        
    public void printString(PrintStream out){      
        out.print(".print_string:\n");
        out.print("\tPOP A\n");//dir mem
        out.print("\tPOP B\n");
        out.print("\tPUSH 0\n");    //return undef
        out.print("\tPUSH A\n");
        out.print(".print_string_loop_01:\n");
        out.print("\tMOV A, [B]\n");
        out.print("\tCMP A, 0\n");
	out.print("\tJE .print_string_exit\n");
	out.print("\tMOV [D], A\n");
	out.print("\tINC D\n");
	out.print("\tINC B\n");
	out.print("\tJMP .print_string_loop_01\n");
        out.print(".print_string_exit:\n");
        out.print("\tRET\n");
    }
    
    public void printBool(PrintStream out){
        out.print(".print_boolean:\n");
 	out.print("\tPOP C\n");
 	out.print("\tPOP B\n");
 	out.print("\tCMP B, 0\n");
	out.print("\tJA .boolean_true\n");
	out.print("\tPUSH .boolean_f\n");
	out.print("\tJMP .boolean_fin\n");
        out.print(".boolean_true:\n");
	out.print("\tPUSH .boolean_t\n");
        out.print(".boolean_fin:\n");
        out.print("\tCALL .print_string\n");
        out.print("\tPUSH C\n");
 	out.print("\tRET\n");
    }
        
    public void printNumber(PrintStream out){ 
        out.print(".print_number:\n");
	out.print("\tPOP B\n");     
	out.print("\tPOP C\n");
	out.print("\tPUSH 0\n");    //undefine return
	out.print("\tPUSH B\n");
	out.print("\tMOV B, 100\n");   
        out.print(".print_number_loop:\n");
	out.print("\tMOV A, C\n");       
	out.print("\tDIV B\n");
	out.print("\tPUSH A\n");
  	out.print("\tADD A, 48\n");
  	out.print("\tMOV [D], A\n");
 	out.print("\tINC D\n");  
	out.print("\tPOP A\n");
	out.print("\tMUL B\n");
	out.print("\tSUB C, A\n");
	out.print("\tMOV A, B\n");
	out.print("\tDIV 10\n");
	out.print("\tMOV B,A\n");  
	out.print("\tCMP B, 0\n");   
	out.print("\tJNZ .print_number_loop\n");   
	out.print("\tRET\n");
    }
    
    public void printClrScr(PrintStream out){
        out.print(".clear_screen:\n");
        out.print("\tPOP C\n");
        out.print("\tPUSH 0\n");     //undefine return
        out.print("\tPUSH C\n");
        out.print("\tMOV D, 232\n");
        out.print(".clear_loop:\n");
        out.print("\tMOV [D], 0\n");
        out.print("\tINC D\n");
        out.print("\tCMP D, 0\n");
        out.print("\tJNZ .clear_loop\n");
        out.print("\tMOV D, 232\n");        
        out.print("\tRET\n");
    }
    
}
