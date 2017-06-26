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
package eightBit.compile;
import eightBit.asm.*;
import java.util.*;
import java.util.stream.Collectors;

public interface AsmEmiter {

    default AsmAst PROGRAM(List<String> error, List<AsmDataBlock> dataArea, List<AsmAst> functions, List<AsmAst> main) {
        return new AsmProgram(error, dataArea, functions, main);
    }

    default AsmAst MAIN(AsmAst body) {
        return new AsmMain(body);
    }

    default AsmAst BLOCK(List<AsmAst> members) {
        return new AsmBlock(members);
    }

    default AsmAst BLOCK() {
        return new AsmBlock(Arrays.asList());
    }

    default AsmAst EMPTY() {
        return new AsmEmpty();
    }

    default AsmAst NUM(int value) {
        return new AsmNum(value);
    }

    default AsmAst STRING(AsmId i) {
        return new AsmString(i.getValue());
    }

    default AsmAst STRING(String value) {
        return new AsmString(value);
    }

    default AsmId ID(String value) {
        return new AsmId(value);
    }

    default AsmFunction FUNCTION(AsmId id, AsmAst formals, AsmAst body, boolean rt) {
        return new AsmFunction(id, formals, body, rt);
    }

    default AsmIf IF(AsmAst c, AsmAst t, AsmAst e, AsmAst i) {
        return new AsmIf(c, t, e, i);
    }

    default AsmWhile WHILE(AsmAst c, AsmAst t, AsmAst i) {
        return new AsmWhile(c, t, i); //T-T
    }

    default AsmCall CALL(AsmAst f, AsmAst args, boolean rt, List<AsmData> l) {
        return new AsmCall(f, args, rt, l);
    }

    default AsmAst OPERATION(AsmAst oper, AsmAst left, AsmAst right) {
        return new AsmOperation(oper, left, right);
    }

    default AsmFor FOR(AsmAst inst, AsmAst condition, AsmAst mod, AsmAst body, AsmAst id) {
        return new AsmFor(inst, condition, mod, body, id);
    }

    default AsmAst ASSIGN(AsmAst left, AsmAst right) {
        return new AsmAssign(left, right);
    }

    default AsmAst ARGUMENTS(List<AsmAst> args) {
        return new AsmArguments(args.stream()
                .map(X -> new AsmArgs(X))
                .collect(Collectors.toList()));
    }

    default AsmAst LET(List<AsmAst> as, AsmAst res) {
        return new AsmLet(as, res);
    }

    default AsmAst ARGUMENTS() {
        return new AsmArguments(Arrays.asList());
    }

    default AsmAst FORMALS() {
        return new AsmFormals(Arrays.asList());
    }

    default AsmAst FORMALS(List<AsmId> args) {
        return new AsmFormals(args);
    }

    default AsmAst RET(AsmAst e) {
        return new AsmReturn(e);
    }

    default AsmAst OPER(String op) {
        return new AsmId(op);
    }

    default AsmAst CMP(AsmAst oper, AsmAst izq, AsmAst der, AsmAst fun) {
        return new AsmCMP(oper, izq, der, fun);
    }

    default AsmData DATA(AsmId name, AsmAst value) {
        return new AsmData(name, value);
    }

    default AsmAst FOLD_LEFT(AsmAst left, AsmAst right) {
        AsmOperation rightOperation = (AsmOperation) right;
        return new AsmOperation(rightOperation.getOper(), left, rightOperation.getRight());
    }

    default AsmRelMonom RELMONOM() {
        return new AsmRelMonom(new ArrayList<>());
    }

    default AsmRelMonom RELMONOM(List<AsmAst> l) {
        return new AsmRelMonom(l);
    }
    
    default AsmRelOperation RELOPERATION(List<AsmAst> l, AsmAst n){
        return new AsmRelOperation(l,n);
    }

    final AsmNum TRUE = new AsmNum(1); 
    final AsmNum FALSE = new AsmNum(0);
    final AsmAst ADD = new AsmId("ADD ");
    final AsmAst MINUS = new AsmId("SUB ");
    final AsmAst INC = new AsmId("INC ");
    final AsmAst DEC = new AsmId("DEC ");
    final AsmAst MUL = new AsmId("MUL ");
    final AsmAst DIV = new AsmId("DIV ");
    final AsmNum UNDEF = new AsmNum(0);
    final AsmNum NULL = new AsmNum(0);
    final AsmAst MAXK = new AsmId("JA ");//carry false zero false
    final AsmAst MINK = new AsmId("JB ");//carry true
    final AsmAst EQUK = new AsmId("JE ");//zero true
    final AsmAst MAXEQU = new AsmId("JAE ");//carry false
    final AsmAst MINEQU = new AsmId("JBE ");//carry true zero true
    final AsmAst DIF = new AsmId("JNE ");//zero false
    final AsmAst NEG = new AsmId("NOT ");//zero false
}
