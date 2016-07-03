/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holodniysvitanok.cmd;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author java
 */
public class MyFilterForFile implements FileFilter {

    private String word;

    public MyFilterForFile(String word) {
        this.word = word;
    }
    
    @Override
    public boolean accept(File pathname) {
        String var = pathname.getName().toLowerCase();
        return var.contains(word.toLowerCase()) && pathname.isFile() ;
    }
    
}
