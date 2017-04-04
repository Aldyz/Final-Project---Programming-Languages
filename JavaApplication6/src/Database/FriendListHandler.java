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
 * This class is used to handle and manage the client's friend list
 * @author Aldi, Vero, Vincent
 */
public class FriendListHandler extends ListHandler{
    
    /**
     * this method is used to check whether the friend that is searched to be added exist or not
     * @param user the client's name
     * @param friend the name of friends to be added
     * @return boolean true if the friend already existed in the client friends list
     */
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
    
    /**
     * This method is used to set friend list on the data base txt file
     * @param list the user's friend lsit
     * @param user the name of the user
     */
    public static void setFriendList(ArrayList<String> list, String user){
        setList(list, user + "FriendsList", "FriendsList");
    }
    
    /**
     * this method is used to append the added friend's name to the client friend list txt file
     * @param user user client's name
     * @param friend friend's name to be added 
     */
    public static void addFriend(String user, String friend){
        insertList(user + "FriendsList", friend, "FriendsList");
        insertList(friend + "FriendsList", user, "FriendsList");
    }
    
    /**
     * This method is used to create new friend list txt file for client who has just signed up
     * @param user client's name
     */
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
        
    /**
     * This method is used to get user's friend list from server txt file
     * @param user client's name
     * @return friend list name
     */
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
