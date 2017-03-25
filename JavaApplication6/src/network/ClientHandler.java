/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.DatabaseFunction;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 *
 * @author Lenovo
 */
public class ClientHandler implements Runnable{

    Socket s;
    String input;
    DataInputStream in;
    DataOutputStream ou;
    
    public ClientHandler(Socket s){
        this.s = s;
        try{
            in = new DataInputStream(s.getInputStream());
            ou = new DataOutputStream(s.getOutputStream());
        }catch(IOException e){
            System.out.println("Error in Client Handler Constructor");
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void run() {
        
        while(s.isConnected()){
            try{
            while(s.isConnected()){

                input = in.readUTF();
                
                if(input.startsWith("SIGNIN")){
                        String[] array = input.split(" ");
                        boolean check = new DatabaseFunction().SignInChecker(array[1], array[2]);
                        ou.writeBoolean(check);
                        ou.flush();
                        if(check){
                            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " has signed in.");
                            break;
                        }else{
                            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " failed  to sign in.");
                        }
                }else if(input.startsWith("SIGNUP")){
                    
                    }
                }
            }catch(IOException e){
                System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " " + e.getMessage());
                break;
            }
        }
        
    }
    
    
}
