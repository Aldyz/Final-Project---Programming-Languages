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
    
    final int port = 8000;
    static ArrayList<Socket> Connected;
    static ArrayList<String> Users;
    ServerSocket ss;
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
                Thread t = new Thread();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
