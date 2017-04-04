/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class ListHandler {
    
    
    protected static void setList(ArrayList<String> list, String name, String file){
        try{
            FileWriter fw = new FileWriter(file + "\\" + name + ".txt",false);
            PrintWriter pw = new PrintWriter(fw);
            for(int i = 0; i < list.size(); i++){
                pw.println(list.get(i));
            }
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    protected static void insertList(String name, String friend, String file){
        try{
            FileWriter fw = new FileWriter(file + "\\" + name + ".txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(friend);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
