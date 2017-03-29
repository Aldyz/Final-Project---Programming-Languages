/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.BlockListHandler;
import Database.DatabaseFunction;
import Database.FriendListHandler;
import com.ConnectedUser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import jdk.nashorn.internal.ir.Block;
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
    ConnectedUser user;
    
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
                            user = new ConnectedUser(userName, s);
                            ChatServer.Connected.add(user);
                            break;
                        }else{
                            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " failed  to sign in.");
                        }
                    }else if(input.startsWith("SIGNUP")){
                        String[] array = input.split(" ");
                        boolean check = DatabaseFunction.SignUpChecker(array[1], array[3]);
                        if(check){
                            DatabaseFunction.Insert(array[1], array[2], array[3]);
                            FriendListHandler.createList(array[1]);
                        }
                        ou.writeBoolean(check);
                        ou.flush();
                        System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " signed up.");
                    }
                    
                    
                    Thread.sleep(1000);
                }
                
                
                ou.writeUTF(FriendListHandler.getFriendList(userName));
                ou.flush();
                ou.writeUTF(BlockListHandler.getBlockList(userName));
                ou.flush();
                
                user.setBlocked(BlockListHandler.getBlockList(userName));
            
            while(true){
                input = in.readUTF();
                String array[] = input.split(" ");
                if(input.startsWith("SEND")){
                    if(BlockListHandler.checkDataExist(array[1], userName))
                        continue;
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendMsg(userName, input.substring(array[0].length() + array[1].length() + 2));
                    }
                        
                }else if(input.startsWith("ADD")){
                    boolean check = FriendListHandler.checkDataExist(userName, array[1]);
                    check = !check;
                    if(check){
                        FriendListHandler.addFriend(userName, array[1]);
                        FriendListHandler.addFriend(array[1], userName);
                    }
                    ChatServer.UpdateFL(array[1], userName);
                    ou.writeUTF("SADDED " + check);
                    ou.flush();
                }else if(input.startsWith("SEARCH")){
                    boolean check = DatabaseFunction.userCheck(array[1]);
                    ou.writeUTF("SRESULT " + check);
                    ou.flush();
                }else if(input.startsWith("BLOCK")){
                    user.addBlocked(array[1]);
                    BlockListHandler.insertList(user.getBlocked(), userName);
                }else if(input.startsWith("UNBLOCK")){
                    user.removeBlocked(array[1]);
                    BlockListHandler.insertList(user.getBlocked(), userName);
                }else if(input.startsWith("CREATEGROUP")){
                    
                }
                Thread.sleep(1000);
            }
        }catch(IOException e){
            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " " + e.getMessage());
        }catch(Exception e) {
            System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " " + e.getMessage());
        }finally{
            ChatServer.Connected.remove(user);
        }    
        
    }
    
    
}
