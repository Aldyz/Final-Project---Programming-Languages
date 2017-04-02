/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import network.ChatClient;
/**
 *
 * @author Lenovo
 */
public class GroupHistoryHandler {
    
    public static void createHistory(String name){
        try{
            FileWriter fw = new FileWriter("GroupHistory\\" + ChatClient.getName()+  "-" + name + "GroupHistory.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void addHistory(String name, String msg){
        try{
            FileWriter fw = new FileWriter("GroupHistory\\" + ChatClient.getName()+  "-" + name + "GroupHistory.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(msg);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static String history(String name){
        String chat = "";
        String check;
        try{
            FileReader fr = new FileReader("GroupHistory\\"+ ChatClient.getName()+  "-"  + name + "GroupHistory.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                chat += check + "\n";
            }
            br.close();
            fr.close();
            
            return chat;
            
        }catch(IOException e){
            createHistory(name);
            System.out.println(e.getMessage());
        }
        return "";
    }
    
    public static void clearHistory(String name){
        try{
            FileWriter fw = new FileWriter("GroupHistory\\"+ ChatClient.getName()+  "-"  + name + "GroupHistory.txt", false);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("");
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
