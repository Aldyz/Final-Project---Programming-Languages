/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 *
 * @author USER
 */
public class RememberMe {
    public static void RememberLogin(String name, String pass){
        try{
            FileWriter fw = new FileWriter("RememberMe.txt");
            PrintWriter pw  = new PrintWriter(fw);
            pw.println(name);
            pw.println(pass);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static String[] getRememberMe(){
        String[] remember = new String[2];
        try{
            FileReader fr = new FileReader("RememberMe.txt");
            BufferedReader br = new BufferedReader(fr);
            remember[0] = br.readLine();
            remember[1] = br.readLine();
            br.close();
            fr.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return remember;
    }
    
}
