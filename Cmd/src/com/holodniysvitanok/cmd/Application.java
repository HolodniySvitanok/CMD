/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holodniysvitanok.cmd;

import java.io.IOException;

/**
 *
 * @author Admin
 */
public class Application {

    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String... args) {
        // TODO code application logic here
        try{
        CommandLine cLine = new CommandLine(args);
        cLine.listenerCommand();
        }catch(IOException ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}


