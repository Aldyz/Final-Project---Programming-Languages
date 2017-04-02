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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class GroupHandler {
    public static boolean isExist(String name){
        File f = new File("GroupList\\" + name + ".txt");
        if(f.exists())
            return true;
        else
            return false;
    }
    
    public static boolean checkDataExist(String name){
        File f = new File("GroupList\\" + name + ".txt");
        if(f.exists())
            return true;
        else
            return false;
    }
    
    public static boolean memberExist(String name, String member){
        String check = "";
        try{
            FileReader fr = new FileReader("GroupList\\"+name+".txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
              if(check.equals(member)){
                  return true;
              }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static void createGroup(String name){
        try{
            FileWriter fw = new FileWriter("GroupList\\"+name+".txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void insertMember(String name, String member){
        try{
            FileWriter fw = new FileWriter("GroupList\\"+name+".txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(member);
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void deleteMember(String name, String member){
        String check = "";
        ArrayList<String> names = new ArrayList<String>();
        try{
            FileReader fr = new FileReader("GroupList\\"+name+".txt");
            BufferedReader br = new BufferedReader(fr);
            while((check = br.readLine()) != null){
              if(check.equals(member)){
                  continue;
              }else{
                  names.add(check);
              }
            }
            br.close();
            fr.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        setList(name, names);
    }
    
    public static void setList(String name, ArrayList<String> members){
        try{
            FileWriter fw = new FileWriter("GroupList\\"+name+".txt");
            PrintWriter pw = new PrintWriter(fw);
            for(int i = 0; i < members.size(); i++){
                pw.println(members.get(i));
            }
            pw.close();
            fw.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println(memberExist("Bonanza", "Ald"));
    }
}
