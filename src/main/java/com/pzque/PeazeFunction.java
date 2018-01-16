package com.pzque;

import java.util.List;

import com.pzque.PeazeParser.*;

public class PeazeFunction {
    public PeazeFunction(List<String> params, SequenceContext body) {
        this.env = null;
        this.params = params;
        this.body = body;
    }

    public PeazeEnv getEnv() {
        return env;
    }

    public List<String> getParams() {
        return params;
    }

    public SequenceContext getBody() {
        return body;
    }

    public int getParamCount() {
        return params.size();
    }

    public void setEnv(PeazeEnv env) {
        this.env = env;
    }

    private PeazeEnv env;
    private List<String> params;
    private SequenceContext body;
}