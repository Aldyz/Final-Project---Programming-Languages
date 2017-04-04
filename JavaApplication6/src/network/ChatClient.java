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
    private static String IP;
    private static String name;
    public static byte[] arr;;
    private static ClientThread ct;

    public static String getName() {
        return name;
    }
    
    public static void stopThread(){
        ct.stop();
    }
    
    public static void logOut(){
        try{
            ou.writeUTF("LOGOUT ");
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
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
    
    public static void changePassword(String newPass){
        try{
            ou.writeUTF("UPDATEPASSWORD " + newPass);
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
            ct = new ClientThread(s);
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
    
    public static void deleteFriend(String user){
        try{
            ou.writeUTF("DELETEFRIEND " + user);
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
    
    public static void getAuthentication(String name, String pass){
        if(ou == null)
            return;
        
        try{
            ou.writeUTF("SIGNIN " + name + " " + pass);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void getRegistration(String name, String password){
        if(ou == null)
            return;
        
        try{
            ou.writeUTF("SIGNUP " + name + " " + password);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    
}
