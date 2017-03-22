/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

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
public class Server {
    
    private static final int port = 8000;
    public static ArrayList<Socket> Connected;
    public static ArrayList<String> Users;
    private static ServerSocket ss;
    private static String input;
    
    public Server(){
        Connected = new ArrayList<Socket>();
        Users = new ArrayList<String>();
    }
    
    public void startServer(){
        System.out.println("Starting Server...");
        try
        {
            ss = new ServerSocket(port);
            System.out.println("Server Started Successfully");
            
            while(true){
                Socket s = ss.accept();
                System.out.println(s.getLocalAddress().getHostName()+"::"+s.getLocalAddress().getHostAddress()+" has connected.");
                DataInputStream in = new DataInputStream(s.getInputStream());
                input = in.readUTF();
                String[] array = input.split(" ");
                if(input.startsWith("SIGNIN")){
                    System.out.println(s.getLocalAddress().getHostName() + "::" + s.getLocalAddress().getHostAddress() + "is signing in.");
                    
                    
                    Thread t = new Thread(new ClientManager(s));
                    System.out.print("Thread going to start ------> ");
                    t.start();
                    System.out.println("Thread has started.");
                }else if(input.startsWith("SIGNUP")){
                    
                }
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
