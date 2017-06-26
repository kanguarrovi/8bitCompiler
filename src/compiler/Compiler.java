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
import eightBit.asm.AsmAst.AST;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.stream.*;

public class Compiler extends EightBitBaseVisitor<AsmAst> implements AsmEmiter {

    protected AsmAst program;
    protected ControlAreas control = new ControlAreas();

    public AsmAst getProgram() {
        return this.program;
    }

    public void genCode() {
        this.program.genCode();
    }

    public AsmAst compile(ParseTree tree) {
        return visit(tree);
    }

    @Override
    public AsmAst visitEightProgram(EightBitParser.EightProgramContext ctx) {
        ctx.eightFunction().stream().forEach(fun -> visit(fun));
        List<AsmAst> pr = ctx.eightMain().stream().map(X -> visit(X)).collect(Collectors.toList());
        control.setDefData();
        return this.program = PROGRAM(ErrorControl.err, control.data, control.funs, pr);
    }

    @Override
    public AsmAst visitEightMain(EightBitParser.EightMainContext ctx) {
        this.control.initData("main");
        return MAIN(visit(ctx.funBody()));
    }

    @Override
    public AsmAst visitEightFunction(EightBitParser.EightFunctionContext ctx) {
        control.initData(ctx.id().getText());
        return control.addFun( FUNCTION(ErrorControl.funNameValid(ctx.id().getText()), 
                                visit(ctx.formals()),visit(ctx.funBody()), 
                                control.getRetr()) );
    }

    @Override
    public AsmAst visitEmptyStatement(EightBitParser.EmptyStatementContext ctx) {
        return EMPTY();
    }

    @Override
    public AsmAst visitCallStatement(EightBitParser.CallStatementContext ctx) {
        switch (ctx.ID().getText()) {
            case "print_number":
                control.defF.num = true;
                break;
            case "print_string":
                control.defF.str = true;
                break;
            case "print_boolean":
                control.setDefBoolFlag();
                break;
            case "clear_screen":
                control.defF.cls = true;
                break;
        }
        AsmFunction fnt = control.getFun(ctx.ID().getText());
        return (fnt != null)?
                CALL(ID(ctx.ID().getText()), visit(ctx.arguments()), fnt.ret, control.searchByCtx())
                :
                CALL(ID(ctx.ID().getText()), visit(ctx.arguments()), false, control.searchByCtx());
    }

    @Override
    public AsmAst visitReturnStatement(EightBitParser.ReturnStatementContext ctx) {
        control.setRetr(true);
        return RET(visit(ctx.expr()));
    }

    @Override
    public AsmAst visitAssignStatement(EightBitParser.AssignStatementContext ctx) {
        AsmAst valor = visit(ctx.expr());
        return (valor.getType() == AST.ID || valor.getType() == AST.OPERATION) ?
                ASSIGN(insertinData((AsmId) visit(ctx.id()), UNDEF), valor)
                :
                ASSIGN(insertinData((AsmId) visit(ctx.id()), valor), valor);
    }

    @Override
    public AsmAst visitBlockStatement(EightBitParser.BlockStatementContext ctx) {
        return (ctx.closedList() == null) ? BLOCK()
                : visit(ctx.closedList());
    }

    @Override
    public AsmAst visitClosedList(EightBitParser.ClosedListContext ctx) {
        return BLOCK(ctx.closedStatement().stream()
                .map(c -> visit(c))
                .collect(Collectors.toList()));
    }

    @Override
    public AsmAst visitFormals(EightBitParser.FormalsContext ctx) {
        return (ctx.idList() == null) ? FORMALS() : visit(ctx.idList());
    }

    public AsmId insertinData(AsmId id, AsmAst valor) {
        String name = control.nameFormals(id.getValue());
        AsmId id2;
        if (ControlAreas.getTablaS(name) != null) {
            id2 = (AsmId) ControlAreas.getTablaS(name);
        } else {
            id2 = ID(name);
            ControlAreas.putTablaS(name, id2);
            control.addData(DATA(id2, valor == null ? UNDEF : valor));
        }
        return id2;
    }

    public AsmId insertDataNoRepeat(AsmId id, AsmAst valor) {
        String name = control.nameFormals(id.getValue());
        AsmId id2;
        if (ControlAreas.getTablaS(name) != null) {
            id2 = (AsmId) ControlAreas.getTablaS(name);
            ErrorControl.repeatValue(name);
        } else {
            id2 = ID(name);
            ControlAreas.putTablaS(name, id2);
        }
        control.addData(DATA(id2, valor == null ? UNDEF : valor));
        return id2;
    }

    @Override
    public AsmAst visitIdList(EightBitParser.IdListContext ctx) {
        return FORMALS(ctx.id().stream()
                .map(c -> insertDataNoRepeat((AsmId) visit(c), null))
                .collect(Collectors.toList()));
    }

    @Override
    public AsmAst visitArguments(EightBitParser.ArgumentsContext ctx) {
        return ctx.args() == null ? ARGUMENTS() : ARGUMENTS(ctx.args().expr()
                .stream().map(c -> visit(c))
                .collect(Collectors.toList()));
    }

    @Override
    public AsmAst visitId(EightBitParser.IdContext ctx) {
        return ID(ctx.ID().getText());
    }

    @Override
    public AsmAst visitArithOperation(EightBitParser.ArithOperationContext ctx) {
        return (ctx.operTDArithMonom() == null)
                ? visit(ctx.arithMonom())
                : ctx.operTDArithMonom().stream().map(c -> visit(c))
                .reduce(visit(ctx.arithMonom()), (opers, expr) -> FOLD_LEFT(opers, expr));
    }

    @Override
    public AsmAst visitArithMonom(EightBitParser.ArithMonomContext ctx) {
        return (ctx.operTDArithSingle() == null)
                ? visit(ctx.arithSingle())
                : ctx.operTDArithSingle().stream().map(c -> visit(c))
                .reduce(visit(ctx.arithSingle()), (opers, expr) -> FOLD_LEFT(opers, expr));
    }

    @Override
    public AsmAst visitOperTDArithSingle(EightBitParser.OperTDArithSingleContext ctx) {
        AsmAst oper = (ctx.oper.getType() == EightBitParser.MUL) ? MUL : DIV;
        return OPERATION(oper, NULL, visit(ctx.arithSingle()));
    }

    @Override
    public AsmAst visitOperTDArithMonom(EightBitParser.OperTDArithMonomContext ctx) {
        AsmAst oper = (ctx.oper.getType() == EightBitParser.ADD) ? ADD : MINUS;
        return OPERATION(oper, NULL, visit(ctx.arithMonom()));
    }

    @Override
    public AsmAst visitArithIdSingle(EightBitParser.ArithIdSingleContext ctx) {
        if (ctx.id() != null && ctx.arguments() != null) {
            AsmFunction fnt = control.getFun(ctx.id().getText());
            return(fnt != null)? 
                 CALL(ID(ctx.id().getText()), visit(ctx.arguments()), fnt.ret, control.searchByCtx())
                 :
                 CALL(ID(ctx.id().getText()), visit(ctx.arguments()), true, control.searchByCtx());
        }
        String name = control.nameFormals(ctx.id().getText());
        if (ControlAreas.getTablaS(name) != null) {
            return ControlAreas.getTablaS(name);
        } else {
            ErrorControl.notDefValue(name);
            return ID(name);
        }
    }

    
    public AsmAst visitInLet(EightBitParser.AssignStatementContext ctx) {
        AsmAst valor = visit(ctx.expr());
        if (valor.getType() == AST.ID || valor.getType() == AST.OPERATION) {
            return ASSIGN(insertDataNoRepeat((AsmId) visit(ctx.id()), UNDEF), valor);
        } else {
            return ASSIGN(insertDataNoRepeat((AsmId) visit(ctx.id()), valor), valor);
        }
    }

    @Override
    public AsmAst visitLetStatement(EightBitParser.LetStatementContext ctx) {
        List<AsmAst> as = ctx.assignStmtList().assignStatement()
                .stream().map(x -> visitInLet(x))
                .collect(Collectors.toList());
        return LET(as, visit(ctx.closedStatement()));
    }

    @Override
    public AsmAst visitForStatement(EightBitParser.ForStatementContext ctx) {
        return FOR(visit(ctx.assignStatement(0)),
                visit(ctx.expr()),
                visit(ctx.assignStatement(1)),
                visit(ctx.closedStatement()),
                ID(ControlAreas.name()));
    }

    @Override
    public AsmAst visitIfStatement(EightBitParser.IfStatementContext ctx) {
        AsmAst e = null;
        if (ctx.closedStatement().size() == 2) {
            e = visit(ctx.closedStatement(1));
        }
        return IF(visit(ctx.expr()),visit(ctx.closedStatement(0)), e, ID(ControlAreas.name()));
    }

    @Override
    public AsmAst visitWhileStatement(EightBitParser.WhileStatementContext ctx) {
        return WHILE(visit(ctx.expr()), visit(ctx.closedStatement()), ID(ControlAreas.name()));
    }

    public AsmAst negVis(EightBitParser.RelOperationContext ctx) {
        return (!(ctx.start.getText().equals("!")))?
                visit(ctx)
                :
                RELOPERATION(new ArrayList<>(), NEG).add(visit(ctx));
    }

    @Override
    public AsmAst visitRelMonom(EightBitParser.RelMonomContext ctx) {
        if (ctx.relOperation().size() > 1 || ctx.relOperation(0).start.getText().equals("!")) {
            AsmRelOperation relOp = RELOPERATION(new ArrayList<>(), null);
            ctx.relOperation().stream().forEach(x -> relOp.add(negVis(x)));
            return relOp;
        } else {
            return super.visitRelMonom(ctx);
        }
    }

    @Override
    public AsmAst visitRelOperation(EightBitParser.RelOperationContext ctx) {
        if (ctx.start != null && ctx.start.getText().equals("!")) {
            return visit(ctx.relOperation());
        }
        return (ctx.arithOperation().size() == 1)?
                visit(ctx.arithOperation(0))
                :
                CMP(visit(ctx.relOperator(0)), visit(ctx.arithOperation(0)),
                    visit(ctx.arithOperation(1)), ID(ControlAreas.name("cmp_")));
    }

    @Override
    public AsmAst visitRelOperator(EightBitParser.RelOperatorContext ctx) {
        return control.operadoresRelacionales(ctx.getText());
    }

    @Override
    public AsmAst visitExprNull(EightBitParser.ExprNullContext ctx) {
        return NULL;
    }
    
    @Override
    public AsmAst visitExprNum(EightBitParser.ExprNumContext ctx) {
        return NUM(Integer.valueOf(ctx.NUMBER().getText()));
    }

    @Override
    public AsmAst visitExprTrue(EightBitParser.ExprTrueContext ctx) {
        return TRUE;
    }

    @Override
    public AsmAst visitExprFalse(EightBitParser.ExprFalseContext ctx) {
        return FALSE;
    }

    @Override
    public AsmAst visitExprString(EightBitParser.ExprStringContext ctx) {
        return STRING(insertinData(ID(String.valueOf(control.cont++)), STRING(ctx.getText())));
    }

    @Override
    public AsmAst visitExpr(EightBitParser.ExprContext ctx) {
        if (ctx.relMonom().size() > 1) {
            AsmRelMonom relMon = RELMONOM();
            ctx.relMonom().stream().forEach(rm -> relMon.add(visit(rm)));
            return relMon;
        } else {
            return super.visitExpr(ctx);
        }
    }

    @Override
    public AsmAst visitArithParsSingle(EightBitParser.ArithParsSingleContext ctx) {
        return visit(ctx.expr());
    }
}
