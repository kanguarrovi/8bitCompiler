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

public class AsmProgram implements AsmAst {
   List<String> dataError       = new ArrayList<>();
   List<AsmDataBlock> dataArea  = new ArrayList<>();
   List<AsmAst> funs            = new ArrayList<>();
   List<AsmAst> main;
   public static final int DIR_D = 232;
   

   
   public AsmProgram(List<String> error,List<AsmDataBlock>data,List<AsmAst>fun,List<AsmAst> main){
       this.dataError = error;
       this.dataArea  = data;
       this.funs      = fun;
       this.main      = main;
       ErrorControl.mainValid(main.size());
   }
   
   @Override
    public AST getType() {
        return AST.PROGRAM;
    }
    
    @Override
    public void genCode(PrintStream out){
        out.print("MOV D,"+DIR_D+"\n");
        out.print("JMP .main\n");
        if(dataError.size()>0){
            out.print("; ------- Error Area ------- \n");
            dataError.stream().forEach(err ->  out.print(err+"\n"));
        }
        out.print("\n; ------- Data Area ------- \n");
        dataArea.stream().forEach(dat ->  dat.genCode(out));
        out.print("\n; ------- Program Area ------- \n");
        this.funs.stream().forEach(fun -> fun.genCode(out));
        out.print("\n; ------- Main Area ------- \n");
        main.stream().forEach(X -> X.genCode(out));
    }
}
