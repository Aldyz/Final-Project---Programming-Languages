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
public class FriendListHandler {
    
    public static boolean checkDataExist(String user, String friend){
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
    
    public static void addFriend(String user, String friend){
        try{
            FileWriter fw = new FileWriter("FriendsList\\" + user + "FriendsList.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            FileWriter fw2 = new FileWriter("FriendsList\\" + friend + "FriendsList.txt", true);
            PrintWriter pw2 = new PrintWriter(fw2);
            pw2.println(user);
            pw.println(friend);
            pw2.close();
            fw2.close();
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void createList(String user){
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
    
    public static void main(String[] args) {
        addFriend("Aldi", "Lawrence");
        String word = getFriendList("Aldi");
        System.out.println(word);
    }
}
