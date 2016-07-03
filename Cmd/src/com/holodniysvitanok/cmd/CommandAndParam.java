/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holodniysvitanok.cmd;

/**
 *
 * @author Admin
 */
public class CommandAndParam {

    private final Keyword kyeword;
    private final String param;
    private final String param2;

    public CommandAndParam(String com, String param, String param2) {
        this.kyeword = Keyword.valueOf(com.toUpperCase());
        this.param = param;
        this.param2 = param2;
    }

    public String getParam() {
        return param;
    }
    public String getParam2() {
        return param2;
    }

    public Keyword getCom() {
        return kyeword;
    }
}

