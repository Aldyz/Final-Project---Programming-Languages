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
    private static String name;
    public static byte[] arr;;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ChatClient.name = name;
    }
    
    private ChatClient(){
        
    }
    
    public static ChatClient getInstance(){
        return new ChatClient();
    }
    
    public static String getBlockList(){
        try{
            return in.readUTF();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static String getFriendList(){
        try{
            return in.readUTF();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static String getGroupList(){
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
    
    public static void fileSender(String name, String friend, int size){
        try{
            ou.writeUTF("FILESEND " + friend + " " + size + " " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void sendData(String name, String fname){
        try{
            ou.writeUTF("SENDFILE " + name + " " + arr.length + " " + fname);
            ou.flush();
            ou.write(arr);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void fileSendConfirm(String name, boolean flag){
        try{
            ou.writeUTF("FILESENDCONFIRM " + name + " " + flag);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void sendGroupMsg(String name, String msg){
        try{
            ou.writeUTF("GSEND " + name + " " + msg);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void groupInvite(String name, String friend){
        try{
            ou.writeUTF("GROUPINVITE " + friend + " " + name);
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
        try{
            ClientThread ct = new ClientThread(s);
            Thread ts = new Thread(ct);
            ts.start();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void addFriend(String name){
        try{
            ou.writeUTF("ADD " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void createGroup(String name){
        try{
            ou.writeUTF("CREATEGROUP " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void blockUser(String name){
        try{
            ou.writeUTF("BLOCK " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void unblockUser(String name){
        try{
            ou.writeUTF("UNBLOCK " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void userSearch(String name){
        try{
            ou.writeUTF("SEARCH " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
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
