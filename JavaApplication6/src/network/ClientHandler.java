/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.DatabaseFunction;
import Database.FriendListHandler;
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
    String userName;
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
        try{
                
                while(true){

                    input = in.readUTF();
                
                    if(input.startsWith("SIGNIN")){
                        String[] array = input.split(" ");
                        boolean check = DatabaseFunction.SignInChecker(array[1], array[2]);
                        ou.writeBoolean(check);
                        ou.flush();
                        if(check){
                            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " has signed in as: " + array[1]);
                            userName = array[1];
                            ChatServer.Connected.add(s);
                            ChatServer.Users.add(array[1]);
                            break;
                        }else{
                            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " failed  to sign in.");
                        }
                    }else if(input.startsWith("SIGNUP")){
                        String[] array = input.split(" ");
                        boolean check = DatabaseFunction.SignUpChecker(array[1], array[3]);
                        if(check){
                            DatabaseFunction.Insert(array[1], array[2], array[3]);
                        }
                        ou.writeBoolean(check);
                        ou.flush();
                        System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " signed up.");
                    }
                    
                    Thread.sleep(1000);
                }
                
                ou.writeUTF(FriendListHandler.getFriendList(userName));
                ou.flush();
            
            while(true){
                input = in.readUTF();
                String array[] = input.split(" ");
                if(input.startsWith("SEND")){
                    
                }else if(input.startsWith("ADD")){
                    boolean check = FriendListHandler.checkDataExist(userName, array[1]);
                    check = !check;
                    if(check)
                        FriendListHandler.addFriend(userName, array[1]);
                    ou.writeBoolean(check);
                    ou.flush();
                }else if(input.startsWith("SEARCH")){
                    boolean check = DatabaseFunction.userCheck(array[1]);
                    ou.writeBoolean(check);
                    ou.flush();
                }
                Thread.sleep(1000);
            }
        }catch(IOException e){
            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " " + e.getMessage());
        }catch(Exception e) {
            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " " + e.getMessage());
        }finally{
            ChatServer.Connected.remove(s);
            ChatServer.Users.remove(userName);
        }    
        
    }
    
    
}
