/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ConnectedUser {
    
    private String Name;
    private Socket s;
    private DataOutputStream ou;
    private ArrayList<String> Friends;
    private ArrayList<String> Groups;
    private ArrayList<String> Blocked;
    
    public ConnectedUser(String Name, Socket s){
        this.s = s;
        this.Name = Name;
        Friends = new ArrayList<String>();
        Groups = new ArrayList<String>();
        Blocked = new ArrayList<String>();
        try{
            ou = new DataOutputStream(s.getOutputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void sendMsg(String friend, String msg){
        try{
            ou.writeUTF("RECEIVEMSG " + friend + " " + msg);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void sendFileNotification(String friend, String name, int size){
        try{
            ou.writeUTF("FILENOTIF " + friend + " " + size + " " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void sendFile(byte[] arr, int size, String name){
        try{
            ou.writeUTF("SENDFILE " + size + " " + name);
            ou.flush();
            ou.write(arr);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void sendFileConfirmation(boolean flag){
        try{
            ou.writeUTF("FILECONFIRM " + flag);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<String> getBlocked(){
        return Blocked;
    }
    
    public void setBlocked(String Blocked){
        String array[] = Blocked.split(" ");
        for(int i = 0; i < array.length; i++){
            this.Blocked.add(array[i]);
        }
    }
    
    public void setGroup(String group){
        String array[] = group.split(" ");
        for(int i = 0; i < array.length; i++){
            this.Groups.add(array[i]);
        }
    }
    
    public void addGroup(String name){
        Groups.add(name);
    }
    
    public void sendGrpMsg(String name, String GroupName, String msg){
        try{
            ou.writeUTF("GRUPMSG " + name + " " + GroupName + " " + msg);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean groupExist(String name){
        for(int i = 0; i < Groups.size(); i++){
            if(Groups.get(i).equals(name))
                return true;
        }
        return false;
    }
    
    public void removeGroup(String name){
        Groups.remove(name);
    }
    
    public void addBlocked(String user){
        Blocked.add(user);
    }
    
    public void removeBlocked(String user){
        Blocked.remove(user);
    }
    
    public void setFriends(String friends){
        String array[] = friends.split(" ");
        for(int i = 0; i < array.length; i++){
            Friends.add(array[i]);
        }
    }
    
    public void addFriend(String friend){
        Friends.add(friend);
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public DataOutputStream getOu() {
        return ou;
    }

    public void setOu(DataOutputStream ou) {
        this.ou = ou;
    }
    
}
