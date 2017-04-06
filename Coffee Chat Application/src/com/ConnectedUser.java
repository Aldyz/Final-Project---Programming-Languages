/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 * this class is used to hold the username, socket, and list of 
 * friends, groups, and blocked user of client
 * @author Aldi, Vero, Vincent
 */
public class ConnectedUser {
    
    private String Name;
    private Socket s;
    private DataOutputStream ou;
    private ArrayList<String> Friends;
    private ArrayList<String> Blocked;
    
    /**
     * this constructor is used to hold client's name and socket then it creates
     * new friends, groups, and blocked list for the client
     * then it creates new data output stream and data input stream for the client
     * @param Name client name that connected to the server
     * @param s the client's socket
     */
    public ConnectedUser(String Name, Socket s){
        this.s = s;
        this.Name = Name;
        Friends = new ArrayList<String>();
        Blocked = new ArrayList<String>();
        try{
            ou = new DataOutputStream(s.getOutputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method called in client handler class and is used to write the string to binary stream
     * @param friend client who send the message
     * @param msg the message that is sent
     */
    public void sendMsg(String friend, String msg){
        try{
            ou.writeUTF("RECEIVEMSG " + friend + " " + msg);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method called in client handler class and is used to write the string to binary stream
     * @param friend the name of friend
     * @param name name of the client who sent the file
     * @param size size of the file
     */
    public void sendFileNotification(String friend, String name, int size){
        try{
            ou.writeUTF("FILENOTIF " + friend + " " + size + " " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return the user's friend list
     */
    public ArrayList<String> getFriends() {
        return Friends;
    }

    /**
     * set the client friend list
     * @param Friends friend list of client that is sent form the server
     */
    public void setFriends(ArrayList<String> Friends) {
        this.Friends = Friends;
    }
    
    /**
     * this method is used to send request from client to send message
     * @param arr array of file byte
     * @param size size of the file
     * @param name name of the file
     */
    public void sendFile(byte[] arr, int size, String name){
        try{
            System.out.println("Sending File");
            ou.writeUTF("SENDFILE " + size + " " + name);
            ou.flush();
            System.out.println("Sending Data");
            ou.write(arr);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request from client to know whether the 
     * file is confirmed or not
     * @param flag return the boolean when the file is confirm
     */
    public void sendFileConfirmation(boolean flag){
        try{
            ou.writeUTF("FILECONFIRM " + flag);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * set the client blocked list
     * @return blocked list of client that is sent form the server
     */
    public ArrayList<String> getBlocked(){
        return Blocked;
    }
    
    /**
     * set the client blocked list
     * @param Blocked list of friend that is blocked by the user
     */
    public void setBlocked(String Blocked){
        String array[] = Blocked.split(" ");
        for(int i = 0; i < array.length; i++){
            this.Blocked.add(array[i]);
        }
    }
    
    /**
     * add new blocked friend
     * @param user blocked user's name
     */
    public void addBlocked(String user){
        Blocked.add(user);
    }
    
    /**
     * remove blocked user
     * @param user blocked user's name
     */
    public void removeBlocked(String user){
        Blocked.remove(user);
    }
    
    /**
     * remove friends
     * @param user removed friend's name
     */
    public void removeFriend(String user){
        Friends.remove(user);
    }

    /**
     * add new friend
     * @param friend added friend's name
     */
    public void addFriend(String friend){
        Friends.add(friend);
    }

    /**
     * @return the client's name
     */
    public String getName() {
        return Name;
    }
    /**
     * set the client's name
     * @param Name client's name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * get the client's socket
     * @return the client's socket
     */
    public Socket getS() {
        return s;
    }

    /**
     * set the client's socket
     * @param s the client's socket
     */
    public void setS(Socket s) {
        this.s = s;
    }

    /**
     * get the client output stream
     * @return the client output stream
     */
    public DataOutputStream getOu() {
        return ou;
    }

    /**
     * set the client's output stream
     * @param ou client output stream
     */
    public void setOu(DataOutputStream ou) {
        this.ou = ou;
    }
    
}
