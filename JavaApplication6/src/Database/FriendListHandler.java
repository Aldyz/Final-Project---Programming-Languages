/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static Database.ListHandler.setList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class FriendListHandler extends ListHandler{
    
    public static boolean checkFriendExist(String user, String friend){
        String check = "";
        try{
            FileReader fr = new FileReader("FriendsList\\" + user + "FriendsList.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                if(check.equals(friend))
                    return true;
            }
            br.close();
            fr.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static void setFriendList(ArrayList<String> list, String user){
        setList(list, user + "FriendsList", "FriendsList");
    }
    
    public static void addFriend(String user, String friend){
        insertList(user + "FriendsList", friend, "FriendsList");
        insertList(friend + "FriendsList", user, "FriendsList");
    }
    
    public static void createFriendList(String user){
        try{
            FileWriter fw = new FileWriter("FriendsList\\" + user + "FriendsList.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
        
    public static String getFriendList(String user){
        String list = "";
        String check = "";
        try{
            FileReader fr = new FileReader("FriendsList\\" + user + "FriendsList.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                list += check + " ";
            }
            br.close();
            fr.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return list;
    }
    
}
