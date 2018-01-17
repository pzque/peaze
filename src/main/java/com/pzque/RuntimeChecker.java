package com.pzque;

import com.pzque.core.PeazeBuiltin;
import com.pzque.core.PeazeEnv;
import com.pzque.core.PeazeProcedure;
import com.pzque.core.PeazeValue;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class RuntimeChecker {
    public static void CheckNotApplicable(ParserRuleContext ctx, PeazeValue value) {
        if (!value.isApplicable()) {
            PeazeError.runtimeError("NotApplicable",
                    "the expression in list head is not applicable",
                    ctx);
        }
    }

    public static void CheckUndefined(ParserRuleContext ctx, PeazeEnv env, String varName) {
        if (!env.contains(varName) && !PeazeBuiltin.buitinMap.containsKey(varName)) {
            PeazeError.runtimeError("undefined",
                    String.format(" cannot reference the identifier '%s' before its definition", varName),
                    ctx);
        }

    }

    public static void CheckVariableReDefine(ParserRuleContext ctx, PeazeEnv env, String varName) {
        if (env.contains(varName)) {
            PeazeError.runtimeError("variable re-define",
                    String.format("variable '%s' already exists, cannot re-define a variable", varName),
                    ctx);
        }
    }

    public static void CheckParamNotMatch(ParserRuleContext ctx, PeazeProcedure procedure, List<PeazeValue> paramValues) {
        int expected = procedure.getParamCount();
        int given = paramValues.size();
        if (expected != given) {
            PeazeError.runtimeError("ParamNotMatch",
                    String.format(" the expected number of arguments does not match the given number\nexpected: %s\ngiven: %s",
                            expected, given),
                    ctx);
        }
    }

    public static void CheckADD(ParserRuleContext ctx, List<? extends PeazeValue> paramValues) {
        checkNumbers("+", ctx, paramValues);
    }

    public static void CheckSUB(ParserRuleContext ctx, List<? extends PeazeValue> paramValues) {
        checkNumbers("-", ctx, paramValues);
    }

    private static void checkNumbers(String op, ParserRuleContext ctx, List<? extends PeazeValue> paramValues) {
        int i = 1;
        for (PeazeValue v : paramValues) {
            if (!v.isNumber()) {
                PeazeError.runtimeError("%s: contract violation",
                        String.format("expected: number?, given: %s, argument position: %s", op, v, i),
                        ctx);
            }
            i++;
        }
    }
}