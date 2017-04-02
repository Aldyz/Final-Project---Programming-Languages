/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.BlockListHandler;
import Database.DatabaseFunction;
import Database.FriendListHandler;
import Database.GroupHandler;
import Database.GroupListHandler;
import com.ConnectedUser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ClientHandler implements Runnable{

    Socket s;
    String input;
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
                            user = new ConnectedUser(array[1], s);
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
                            GroupListHandler.createList(array[1]);
                        }
                        ou.writeBoolean(check);
                        ou.flush();
                        System.out.println(s.getInetAddress().getHostName() + "::" + s.getInetAddress().getHostAddress() + " signed up.");
                    }
                    
                    
                    Thread.sleep(1000);
                }
                
                
                ou.writeUTF(FriendListHandler.getFriendList(user.getName()));
                ou.flush();
                ou.writeUTF(BlockListHandler.getBlockList(user.getName()));
                ou.flush();
                ou.writeUTF(GroupListHandler.getList(user.getName()));
                ou.flush();
                
                user.setGroup(GroupListHandler.getList(user.getName()));
                user.setBlocked(BlockListHandler.getBlockList(user.getName()));

            while(true){
                input = in.readUTF();
                String array[] = input.split(" ");
                System.out.println(input);
                if(input.startsWith("SEND ")){
                    if(BlockListHandler.checkDataExist(array[1], user.getName()))
                        continue;
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendMsg(user.getName(), input.substring(array[0].length() + array[1].length() + 2));
                    }
                        
                }else if(input.startsWith("ADD")){
                    boolean check = FriendListHandler.checkDataExist(user.getName(), array[1]);
                    check = !check;
                    if(check){
                        FriendListHandler.addFriend(user.getName(), array[1]);
                        FriendListHandler.addFriend(array[1], user.getName());
                        user.addFriend(array[1]);
                    }
                    ChatServer.UpdateFL(array[1], user.getName());
                    ou.writeUTF("SADDED " + check);
                    ou.flush();
                }else if(input.startsWith("SEARCH")){
                    boolean check = DatabaseFunction.userCheck(array[1]);
                    ou.writeUTF("SRESULT " + check);
                    ou.flush();
                }else if(input.startsWith("BLOCK")){
                    user.addBlocked(array[1]);
                    BlockListHandler.insertList(user.getBlocked(), user.getName());
                }else if(input.startsWith("UNBLOCK")){
                    user.removeBlocked(array[1]);
                    BlockListHandler.insertList(user.getBlocked(), user.getName());
                }else if(input.startsWith("CREATEGROUP")){
                    boolean flag = GroupHandler.isExist(array[1]);
                    flag = !flag;
                    if(flag){
                        GroupListHandler.insertMember(user.getName(), input.substring(array[0].length()+1));
                        GroupHandler.createGroup(input.substring(array[0].length()+1));
                        user.addGroup(input.substring(array[0].length()+1));
                    }
                    System.out.println(flag);
                    ou.writeUTF("CREATGRUPRSLT " + flag);
                    ou.flush();
                }else if(input.startsWith("FILESEND ")){
                    if(BlockListHandler.checkDataExist(array[1], user.getName()))
                        continue;
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendFileNotification(user.getName(), input.substring(array[0].length() + 3 + array[1].length() + array[2].length()), Integer.parseInt(array[2]));
                    }
                }else if(input.startsWith("FILESENDCONFIRM")){
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        ChatServer.Connected.get(index).sendFileConfirmation(Boolean.parseBoolean(array[2]));
                    }
                }else if(input.startsWith("SENDFILE")){
                    int index = ChatServer.userExist(array[1]);
                    if(index != -1){
                        byte arr[] = new byte[Integer.parseInt(array[2])];
                        in.readFully(arr);
                        ChatServer.Connected.get(index).sendFile(arr, Integer.parseInt(array[2]),input.substring(array[0].length() + 3 + array[1].length() + array[2].length()));
                    }
                    
                }else if(input.startsWith("GSEND")){
                    ArrayList<Integer> indexs;
                    indexs = ChatServer.getGroupExist(array[1]);
                    for(int i = 0; i < indexs.size(); i++){
                        ChatServer.Connected.get(i).sendGrpMsg(user.getName(), array[1],input.substring(array[0].length() + array[0].length() + 2));
                    }
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
