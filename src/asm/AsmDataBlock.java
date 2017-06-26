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
import java.util.List;

public class AsmDataBlock implements AsmAst {

    AsmId nameFunc;
    List<AsmData> data = new ArrayList<>();

    public AsmDataBlock(String name) {
        nameFunc = new AsmId("." + name + "_data");
    }

    public boolean isName(String name) {
        return nameFunc.getValue().equals("." + name + "_data");
    }

    public AsmDataBlock add(AsmData dat) {
        data.add(dat);
        return this;
    }

    @Override
    public AST getType() {
        return AST.DATABLOCK;
    }

    @Override
    public void genCode(PrintStream out) {
        nameFunc.genCode(out);
        out.print(":\n");
        data.stream().forEach(dat -> dat.genCode(out));
        out.print("\n");
    }

}
