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
 * this class is used to start the server, handle client request, and check 
 * whether the user exist or not
 * @author Aldi, Vero, Vincent
 */
public class ChatServer{
    
    private static final int port = 8000;
    public static ArrayList<ConnectedUser> Connected;
    private static ServerSocket ss;
   
    /**
     * this constructor is used to create new list of connected user
     */
    public ChatServer(){
        Connected = new ArrayList<ConnectedUser>();
    }
    
    /**
     * this method is used to create new server socket to handle the client's socket
     */
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
    
    /**
     * this method is used to check whether there are any clients that want to 
     * connect and run the thread
     */
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
    
    /**
     * this method is used to send user request that they want to update their 
     * friend list
     * @param user the name of client
     * @param added the name of friends to be added
     * @throws IOException 
     */
    public static void UpdateFL(String user, String added)throws IOException{
        int index = userExist(user);
        if(index!=-1){
            Connected.get(index).getOu().writeUTF("UPDATEFL " + added);
        }
    }
    
     /**
      * this method is used to check whether a user exist or not
      * @param name name of the searched user
      * @return return 1 if the user exist
      */
     public static int userExist(String name){
         for(int i = 0; i < Connected.size(); i++){
             if(Connected.get(i).getName().equals(name))
                 return i;
         }
         
         return -1;
     }
     
   
}