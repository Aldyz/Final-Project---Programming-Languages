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
/**
 * this class is used to handle the client's requests
 * @author Aldi, Vero, Vincent
 */
public class ClientHandler implements Runnable{

    Socket s;
    String input;
    DataInputStream in;
    DataOutputStream ou;
    ConnectedUser user;
    
    /**
     * this method is used to create new data output stream and new data inputstream for the client
     * and set the client's socket
     * @param s the client's sockets
     */
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
    
    /**
     * this method is override method from runable class which function is to 
     * run whenever class Thread use this object as its parameter and call the 
     * start function
     * this method will always check for client request and run the function 
     * within the request
     */
    @Override
    public void run() {
        try{    
                

            while(true){
                input = in.readUTF();
                String array[] = input.split(" ");
                
                if(input.startsWith("SIGNIN ")){
                    
                    int index = ChatServer.userExist(array[1]);
                    
                    if(index!=-1){
                        ou.writeUTF("SIGNINRSLTMSG Someone is currently using the account.");
                        continue;
                    }
                    
                    boolean check = DatabaseFunction.SignInChecker(array[1], array[2]);
                    ou.writeUTF("SIGNINRSLT " + check);
                    ou.flush();
                    if(check){
                        System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " has signed in as: " + array[1]);
                        user = new ConnectedUser(array[1], s);
                        ChatServer.Connected.add(user);
                        ou.writeUTF(FriendListHandler.getFriendList(user.getName()));
                        ou.flush();
                        ou.writeUTF(BlockListHandler.getBlockList(user.getName()));
                        ou.flush();
                        
                        user.setBlocked(BlockListHandler.getBlockList(user.getName()));
                        
                    }else{
                         System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " failed  to sign in.");
                    }
                    
                }else if(input.startsWith("SIGNUP ")){
                    
                    boolean check = DatabaseFunction.SignUpChecker(array[1]);
                    if(check){
                        DatabaseFunction.Insert(array[1], array[2]);
                        FriendListHandler.createFriendList(array[1]);
                        BlockListHandler.createBlockList(array[1]);
                    }
                    ou.writeUTF("SIGNUPRSLT " + check);
                    ou.flush();
                    System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " signed up.");
                    
                }else if(input.startsWith("SEND ")){
                    
                    if(BlockListHandler.checkBlockExist(array[1], user.getName()))
                        continue;
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendMsg(user.getName(), input.substring(array[0].length() + array[1].length() + 2));
                    }
                        
                }else if(input.startsWith("ADD ")){
                    
                    boolean check = FriendListHandler.checkFriendExist(user.getName(), array[1]);
                    check = !check;
                    if(check){
                        FriendListHandler.addFriend(user.getName(), array[1]);
                        user.addFriend(array[1]);
                    }
                    ChatServer.UpdateFL(array[1], user.getName());
                    ou.writeUTF("SADDED " + check);
                    ou.flush();
                    
                }else if(input.startsWith("SEARCH ")){
                    
                    boolean check = DatabaseFunction.userCheck(array[1]);
                    ou.writeUTF("SRESULT " + check);
                    ou.flush();
                    
                }else if(input.startsWith("BLOCK ")){
                    
                    user.addBlocked(array[1]);
                    BlockListHandler.addBlockList(user.getName(), array[1]);
                    
                }else if(input.startsWith("UNBLOCK ")){
                    
                    user.removeBlocked(array[1]);
                    BlockListHandler.setBlockList(user.getBlocked(), user.getName());
                    
                }else if(input.startsWith("FILESEND ")){
                    
                    if(BlockListHandler.checkBlockExist(array[1], user.getName()))
                        continue;
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendFileNotification(user.getName(), input.substring(array[0].length() + 3 + array[1].length() + array[2].length()), Integer.parseInt(array[2]));
                    }
                    
                }else if(input.startsWith("FILESENDCONFIRM ")){
                    
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendFileConfirmation(Boolean.parseBoolean(array[2]));
                    }
                    
                }else if(input.startsWith("SENDFILE ")){
                    
                    int index = ChatServer.userExist(array[1]);
                    System.out.println(ChatServer.Connected.get(index).getName());
                    if(index != -1){
                        byte arr[] = new byte[Integer.parseInt(array[2])];
                        in.readFully(arr);
                        ChatServer.Connected.get(index).sendFile(arr, Integer.parseInt(array[2]),input.substring(array[0].length() + 3 + array[1].length() + array[2].length()));
                    }
                    
                }else if(input.startsWith("UPDATEPASSWORD ")){
                    
                    DatabaseFunction.Update(array[1], user.getName());
                    
                }else if(input.startsWith("DELETEFRIEND ")){
                    
                    user.removeFriend(array[1]);
                    FriendListHandler.setFriendList(user.getFriends(), user.getName());
                    
                }else if(input.startsWith("LOGOUT ")){
                    ChatServer.Connected.remove(user);
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
