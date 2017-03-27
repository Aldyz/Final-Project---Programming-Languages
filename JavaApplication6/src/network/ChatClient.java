/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class ChatClient {
    
    public static Socket s;
    public static DataInputStream in;
    public static DataOutputStream ou;
    private static boolean Authentication;
    private static String IP;
    
    private ChatClient(){
        
    }
    
    public static ChatClient getInstance(){
        return new ChatClient();
    }
    
    public static String getFriendList(){
        try{
            return in.readUTF();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static void setIP(){
        IP = JOptionPane.showInputDialog("Input IP Address");
        if(IP == null)
            IP = "";
    }
    
    public static void sendMsg(String friend, String msg){
        try{
            ou.writeUTF("SEND " + friend + " " + msg);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void Connect(){
        try{
            s = new Socket();
            s.connect(new InetSocketAddress(IP, 8000), 1000);
            ou = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public static void Connect(String IP){
        try{
            s = new Socket();
            s.connect(new InetSocketAddress(IP, 8000), 1000);
            ou = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void startThread(){
        Thread t = new Thread(new ClientThread());
        t.start();
    }
    
    public static boolean addFriend(String name){
        try{
            ou.writeUTF("ADD " + name);
            ou.flush();
            return in.readBoolean();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static boolean userSearch(String name){
        try{
            ou.writeUTF("SEARCH " + name);
            ou.flush();
            return in.readBoolean();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    
    public static boolean getAuthentication(String name, String pass){
        if(ou == null)
            return false;
        
        try{
            ou.writeUTF("SIGNIN " + name + " " + pass);
            ou.flush();
            Authentication = in.readBoolean();
            return Authentication;
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
            return false;
    }
    
    public static boolean getRegistration(String name, String password, String email){
        if(ou == null)
            return false;
        
        try{
            ou.writeUTF("SIGNUP " + name + " " + password + " " + email);
            ou.flush();
            return in.readBoolean();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
}
