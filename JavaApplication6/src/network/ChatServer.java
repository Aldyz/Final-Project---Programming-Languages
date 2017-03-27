/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.DatabaseFunction;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ChatServer implements Runnable{
    
    private static final int port = 8000;
    public static ArrayList<Socket> Connected;
    public static ArrayList<String> Users;
    private static ServerSocket ss;
    private static Socket s;
    
    public ChatServer(){
        Connected = new ArrayList<Socket>();
        Users = new ArrayList<String>();
    }

    @Override
    public void run() {
        
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
   
}