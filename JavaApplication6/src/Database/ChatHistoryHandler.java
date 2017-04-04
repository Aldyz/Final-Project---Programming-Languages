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
import network.ChatClient;

/**
 *this class is used to handle all of the friends chat history
 * @author Aldi, Vero, Vincent
 */
public class ChatHistoryHandler {
    
    /**
     * this method is used to append friends chat history to server txt file
     * @param friend the name of the friend whose message is going to be appended
     * @param msg the message to be appended
     */
    public static void addHistory(String friend, String msg){
        try{
            FileWriter fw = new FileWriter("ChatHistory\\" + ChatClient.getName() + "-" + friend + "History.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(msg);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to create new friends file chat history in the server
     * @param friend name of user's friend
     */
    public static void createHistory(String friend){
        try{
            FileWriter fw = new FileWriter("ChatHistory\\" + ChatClient.getName() + "-" + friend + "History.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to open the friends file chat history and return it
     * @param Friend friend's name whose chat history need to be returned
     * @return return chat history
     */
    public static String getHistory(String Friend){
        String chat = "";
        String check;
        try{
            FileReader fr = new FileReader("ChatHistory\\" + ChatClient.getName() + "-" + Friend + "History.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                chat += check + "\n";
            }
            br.close();
            fr.close();
            
            return chat;
            
        }catch(IOException e){
            createHistory(Friend);
            System.out.println(e.getMessage());
        }
        return "";
    }
    
    /**
     * this method is used to clear friend's chat history in the server txt file
     * @param Friend friends name whose chat history needs to be deleted
     */
    public static void clearHistory(String Friend){
        try{
            FileWriter fw = new FileWriter("ChatHistory\\" + ChatClient.getName() + "-" + Friend + "History.txt", false);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("");
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
}
