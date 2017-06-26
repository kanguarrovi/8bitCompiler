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

import java.util.ArrayList;
import java.util.List;

public class ErrorControl {
    public static List<String> err = new ArrayList<>();
    static final int MAXNUM =  255;
    static final int MINNUM =    0;
    static final int MAXSTRING = 26;
    
    
    public static void stringValid(String str){
        if(str.length()>MAXSTRING){
            err.add("; Warning: "+str + " is too long");
        }
    }
    
    public static AsmId funNameValid(String name){
        name = "."+name;
        AsmId id2;
        if (ControlAreas.getTablaS(name) != null) {
            id2 = (AsmId) ControlAreas.getTablaS(name);
            err.add("; "+name + " function already exists");
        } else {
            id2 = new AsmId(name);
            ControlAreas.putTablaS(name, id2);
        }
        return id2;
    }
    
    public static void notDefValue(String name){
        err.add("; "+name + " is not defined in ."+ ControlAreas.ctxN);
    }
    
    public static void repeatValue(String name){
        err.add("; "+name + " already exists in ." + ControlAreas.ctxN);
   }
    
    public static void mainValid(int size){
        if (size > 1) {
            err.add("; main is already defined \n");
        }else if(size == 0){
            err.add("; main is not defined \n");
        } 
   }
    
    public static void numValid(int num){
        if (MINNUM > num || num > MAXNUM) {
            err.add("; "+num+ " out of range ["+MINNUM+","+MAXNUM+"] in ."+ControlAreas.ctxN);
        }
    }
    
}
