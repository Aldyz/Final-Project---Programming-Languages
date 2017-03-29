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
 *
 * @author Lenovo
 */
public class BlockListHandler {
    
    public static boolean checkDataExist(String user, String friend){
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
            createList(user);
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static void insertList(ArrayList<String> list, String user){
        try{
            FileWriter fw = new FileWriter("BlockList\\" + user + "BlockList.txt",false);
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
            createList(user);
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    public static void createList(String user){
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
