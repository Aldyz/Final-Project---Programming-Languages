/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import com.ConnectedUser;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ChatServer{
    
    private static final int port = 8000;
    public static ArrayList<ConnectedUser> Connected;
    private static ServerSocket ss;
    
    public ChatServer(){
        Connected = new ArrayList<ConnectedUser>();
    }
    
    
    public void startServer(){
        System.out.println("Starting Server...");
        try
        {
            ss = new ServerSocket(port);
            System.out.println("Server Started Successfully");
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
            
    }
    
    public void check(){
        while(true){
            try{
            
                Socket s = ss.accept();
                ClientHandler ch = new ClientHandler(s);
                System.out.println(s.getInetAddress().getHostName()+"::"+s.getInetAddress().getHostAddress()+" has connected.");
                Thread T = new Thread(ch);
                T.start();
            }catch(IOException e){
                System.out.println(e.getMessage());
                break;
            }
        }
    }
    
    public static void UpdateFL(String user, String added)throws IOException{
        int index = userExist(user);
        if(index!=-1){
            Connected.get(index).getOu().writeUTF("UPDATEFL " + added);
        }
    }
    
     
     public static int userExist(String name){
         for(int i = 0; i < Connected.size(); i++){
             if(Connected.get(i).getName().equals(name))
                 return i;
         }
         
         return -1;
     }
     
   
}