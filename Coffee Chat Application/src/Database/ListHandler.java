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
 * This class is used to be extended by FriendListHandler class and BlockListHanlder class
 * @author Aldi, Vero, Vincent
 */
public class ListHandler {
    
    /**
     * this method is used to set list of name
     * @param list list of name that wants to be appended
     * @param name name of the user that wish their list to be write to the server file
     * @param file type of file either friend list or blocked list
     */
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
    
    /**
     * This method is used to append the name of friends that is either blocked or added
     * @param name name of the client
     * @param friend client's friend's name
     * @param file type of file either block list or friend list
     */
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
