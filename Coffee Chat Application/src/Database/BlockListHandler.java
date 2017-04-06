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

/**
 * this class is used to handle the blocked list
 * @author Aldi, Vero, Vincent
 */
public class BlockListHandler extends ListHandler{
    
    /**
    * this method is used to check whether the friend is in the blocked list or not from the server block list txt file
    * @param user the client that want to check the blocked list
    * @param friend the friends that want to be checked
    * @return boolean true if the friends exist in the blocked list
    */
    public static boolean checkBlockExist(String user, String friend){
         String check = "";
        try{
            FileReader fr = new FileReader("BlockList\\" + user + "BlockList.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                if(check.equals(friend))
                    return true;
            }
            br.close();
            fr.close();
        }catch(IOException e){
            createBlockList(user);
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * this method is used to set the parameter in the setList method in list handler
     * @param list user's blocked friends array list
     * @param user user that it's blocked friend list is renewed
     */
    public static void setBlockList(ArrayList<String> list, String user){
        setList(list, user + "BlockList", "BlockList");
    }
    
    /**
     * this method is used to add new blocked friends name in the server file
     * @param name the user's name
     * @param friend friend's name to be blocked
     */
    public static void addBlockList(String name, String friend){
        insertList(name + "BlockList", friend, "BlockList");
    }
    
    /**
     * this method is used to read the user blocked list from server's file
     * @param user client whose blocked list to be returned
     * @return the user's blocked list
     */
    public static String getBlockList(String user){
        String list = "";
        String check = "";
        try{
            FileReader fr = new FileReader("BlockList\\" + user + "BlockList.txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
                list += check + " ";
            }
            br.close();
            fr.close();
        }catch(IOException e){
            createBlockList(user);
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    /**
     * this method is used to create new user's blocked friend list
     * @param user user that does not have blocked friend list
     */
    public static void createBlockList(String user){
        try{
            FileWriter fw = new FileWriter("BlockList\\" + user + "BlockList.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
