/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class ChatHistoryHandler {
    public static void addHistory(String friend, String msg){
        try{
            FileWriter fw = new FileWriter("ChatHistory\\" + friend + "History.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(msg);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void createHistory(String friend){
        try{
            FileWriter fw = new FileWriter("ChatHistory\\" + friend + "History.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static String getHistory(String Friend){
        String chat = "";
        String check;
        try{
            FileReader fr = new FileReader("ChatHistory\\" + Friend + "History.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                chat += check;
            }
            br.close();
            fr.close();
            
            return chat;
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return "";
    }
    
    public static void main(String[] args) {
        
    }
}
