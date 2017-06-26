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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AsmCall implements AsmAst {

    private AsmAst f;
    public AsmAst args;
    public boolean retorno;
    public boolean byMain;
    private List<AsmData> listDat = new ArrayList<>(); 

    public AsmCall(AsmAst f, AsmAst args, boolean rt, List<AsmData> l) {
        this.listDat = l;
        this.f = f;
        this.args = args;
        this.retorno = rt;
        this.byMain = ControlAreas.ctxN.equals("main");
    }

    @Override
    public AST getType() {
        return AST.CALL;
    }
   
    @Override
    public void genCode(PrintStream out) {
        if(!byMain){
            listDat.stream().forEach(d->d.getName().printParams(out, "PUSH"));
            out.print("\tPUSH [.system_area]\n");
        }
        args.genCode(out);
        out.print("\tCALL .");
        f.genCode(out);
        out.print("\n");
        if(!retorno) out.print("\tPOP A\n");
            
        if(!byMain){
            if(retorno)
                out.print("\tPOP B\n");
                
            out.print("\tPOP C\n");
            out.print("\tMOV [.system_area], C\n");
            
            listDat.stream().collect(Collectors.toCollection(LinkedList::new))
                .descendingIterator().forEachRemaining(d -> {out.print("\tPOP A\n");
                                                             out.print("\tMOV [");
                                                             d.getName().genCode(out); 
                                                             out.print("], A\n");});
            if(retorno)
                out.print("\tPUSH B\n");
        }
    }
}
