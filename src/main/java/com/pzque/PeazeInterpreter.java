package com.pzque;

import com.pzque.PeazeParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class PeazeInterpreter {
    private PeazeEnv GlobalEnv;
    private PeazeEnv CurEnv;

    public PeazeInterpreter() {
        this.GlobalEnv = new PeazeEnv(null);
    }

    public PeazeValue eval(ParseTree ast) {
        return PeazeValue.UNDEFINED;
    }

    /* ---------------eval function call --------------- */
    public PeazeValue evalExprApply(ExprApplyContext ctx) {
        return PeazeValue.UNDEFINED;
    }

    public PeazeValue evalBuiltinApply(BuiltinApplyContext ctx) {
        return PeazeValue.UNDEFINED;
    }

    public PeazeValue evalLambdaApply(LambdaApplyContext ctx) {
        return PeazeValue.UNDEFINED;
    }


    /* --------------- eval definition --------------- */
    public PeazeValue evalLambdaDefine(LambdaDefineContext ctx) {
        String funcName = ctx.symbol().getText();
        List<SymbolContext> paramSymbolContextList = ctx.lambda().symbol();
        SequenceContext body = ctx.lambda().sequence();
        return this.funcDefHelp(funcName, paramSymbolContextList, body);
    }

    public PeazeValue evalFuncDefine(FuncDefineContext ctx) {
        List<SymbolContext> symbolContextList = ctx.symbol();
        SequenceContext body = ctx.sequence();
        String funcName = symbolContextList.remove(0).getText();
        return this.funcDefHelp(funcName, symbolContextList, body);
    }

    public PeazeValue funcDefHelp(String funcName,
                                  List<SymbolContext> paramSymbolContextList,
                                  SequenceContext body) {
        PeazeEnv env = new PeazeEnv(this.CurEnv);
        paramSymbolContextList.size();
        List<String> nameList = new ArrayList<>();
        for (SymbolContext symbolContext : paramSymbolContextList)
            nameList.add(symbolContext.getText());
        PeazeFunction function = new PeazeFunction(env, nameList, body);
        PeazeValue value = new PeazeValue(function);
        this.CurEnv.insert(funcName, value);
        return PeazeValue.UNDEFINED;
    }

    public PeazeValue evalVarDefine(VarDefineContext ctx) {
        String name = ctx.symbol().getText();
        PeazeValue value = this.eval(ctx.expr());
        this.CurEnv.insert(name, value);
        return PeazeValue.UNDEFINED;
    }

    /* --------------- eval literal --------------- */
    public PeazeValue evalIntegerLiteral(IntegerLiteralContext ctx) {
        String text = ctx.getText();
        Integer value = Integer.parseInt(text, 10);
        return new PeazeValue(value);
    }

    public PeazeValue evalBooleanLiteral(BooleanLiteralContext ctx) {
        String text = ctx.getText();
        Boolean value = Boolean.parseBoolean(text);
        return new PeazeValue(value);
    }

    public PeazeValue evalDecimalLiteral(DecimalLiteralContext ctx) {
        String text = ctx.getText();
        Double value = Double.parseDouble(text);
        return new PeazeValue(value);
    }
}
