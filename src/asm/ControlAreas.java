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

import static eightBit.compile.AsmEmiter.DIF;
import static eightBit.compile.AsmEmiter.EQUK;
import static eightBit.compile.AsmEmiter.MAXEQU;
import static eightBit.compile.AsmEmiter.MAXK;
import static eightBit.compile.AsmEmiter.MINEQU;
import static eightBit.compile.AsmEmiter.MINK;
import java.util.*;

public class ControlAreas {

    public List<AsmAst>       funs = new ArrayList<>();
    public List<AsmDataBlock> data = new ArrayList<>();
    public AsmDefaultFuns     defF = new AsmDefaultFuns();

    static public HashMap<String, AsmAst> vars = new HashMap<>();
    static public String ctxN = "";
    static public int cont = 0;

    private boolean retr = false;
    private final Map<String, AsmAst> fswitch = new HashMap<>();

    public ControlAreas() {
        fswitch.put("<", MINK);
        fswitch.put(">", MAXK);
        fswitch.put(">=", MAXEQU);
        fswitch.put("<=", MINEQU);
        fswitch.put("==", EQUK);
        fswitch.put("!=", DIF);
    }

    public void initData(String nameFun) {
        cont = 0;
        ctxN = nameFun;
        retr = false;
    }

    public AsmAst addFun(AsmAst fun) {
        funs.add(fun);
        return fun;
    }

    public void setDefBoolFlag() {
        if (!defF.bool) {
            AsmDataBlock dBlock = new AsmDataBlock("boolean");
            dBlock.add(new AsmData(new AsmId(".boolean_t"), new AsmString("\"true\"")));
            dBlock.add(new AsmData(new AsmId(".boolean_f"), new AsmString("\"false\"")));
            defF.bool = true;
            data.add(dBlock);
        }
    }

    public void setDefData() {
        if (funs.size() >= 1) {
            AsmDataBlock dBlock = new AsmDataBlock("System_Area");
            dBlock.add(new AsmData(new AsmId(".system_area"), new AsmNum(0)));
            data.add(dBlock);
        }
        funs.add(defF);
    }

    public AsmFunction getFun(String f) {
        return this.funs.stream().map(x -> (AsmFunction) x)
                .filter(fn -> fn.name.getValue().equals(f))
                .findFirst().orElseGet(() -> null);
    }

    public void addData(AsmData dat) {
        AsmDataBlock it = this.data.stream().filter(da -> da.isName(ctxN))
                .findFirst().orElseGet(() -> null);
        if (it == null) {
            data.add(new AsmDataBlock(ctxN).add(dat));
        } else {
            it.add(dat);
        }
    }

    public List<AsmData> searchByCtx() {
        AsmDataBlock dat = this.data.stream().filter(da -> da.isName(ctxN))
                .findFirst().orElseGet(() -> null);
        return dat == null ? new ArrayList<>() : dat.data;
    }

    public boolean getRetr() {
        return retr;
    }

    public void setRetr(boolean re) {
        retr = re;
    }

    public String nameFormals(String name) {
        return "." + ctxN + "_" + name;
    }

    public AsmAst operadoresRelacionales(String oper) {
        return fswitch.get(oper);
    }

    static public String name() {
        return "." + ctxN + "_" + String.valueOf(cont++);
    }

    static public String name(String name) {
        return "." + ctxN + "_" + name + String.valueOf(cont++);
    }

    static public AsmAst getTablaS(String key) {
        return vars.get(key);
    }

    static public void putTablaS(String name, AsmAst id){
        vars.put(name, id);
    }

}
