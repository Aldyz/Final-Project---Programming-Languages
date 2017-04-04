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
 * this class is used to create new client and handle everything corresponds to 
 * client including clients request
 * @author Aldi, Vero, Vincent
 */
public class ChatClient {
    
    public static Socket s;
    public static DataInputStream in;
    public static DataOutputStream ou;
    private static String IP;
    private static String name;
    public static byte[] arr;
    private static ClientThread ct;

    /**
     * @return the client's name
     */
    public static String getName() {
        return name;
    }
    
    /**
     * this method is used to stop client's thread when the
     * ip button action preformed
     */
    public static void stopThread(){
        ct.stop();
    }
    
    /**
     * this method send the request from client to server to logout
     */
    public static void logOut(){
        try{
            ou.writeUTF("LOGOUT ");
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * this method is used to set the client's name
     * @param name the client's name
     */
    public static void setName(String name) {
        ChatClient.name = name;
    }
    
    private ChatClient(){
        
    }
    
    /**
     * this method is used to get new instance of chat client
     * @return 
     */
    public static ChatClient getInstance(){
        return new ChatClient();
    }
    
    /**
     * this method is used to get the block list
     * @return string of blocked friend list
     */
    public static String getBlockList(){
        try{
            return in.readUTF();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * this method is used to get the friend list
     * @return string of friend list
     */
    public static String getFriendList(){
        try{
            return in.readUTF();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * this method is used to show input windows to get the ip address
     */
    public static void setIP(){
        IP = JOptionPane.showInputDialog("Input IP Address");
        if(IP == null)
            IP = "";
    }
    
    /**
     * this method is used to send request that client wants to send
     * message to a friend
     * @param friend name of the friend
     * @param msg message that is sent
     */
    public static void sendMsg(String friend, String msg){
        try{
            ou.writeUTF("SEND " + friend + " " + msg);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to send a file to
     * a friend
     * @param name file name
     * @param friend friends that will receive the file
     * @param size an estimate of the number of remaining bytes that can be read
     *             (or skipped over) from this input stream without blocking.
     */
    public static void fileSender(String name, String friend, int size){
        try{
            ou.writeUTF("FILESEND " + friend + " " + size + " " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to send a file to
     * a friend 
     * @param name name of friend that we want to send the file to
     * @param fname the file name
     */
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
    
    /**
     * this method is used to send request from client whether their files is confirmed or not
     * @param name name friend that receive the file
     * @param flag boolean whether the file is accepted or not
     */
    public static void fileSendConfirm(String name, boolean flag){
        try{
            ou.writeUTF("FILESENDCONFIRM " + name + " " + flag);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to change their password
     * @param newPass the new password
     */
    public static void changePassword(String newPass){
        try{
            ou.writeUTF("UPDATEPASSWORD " + newPass);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to connect the user to a certain server
     */
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
    
    /**
     * this method is called when the friends form is created and start the run
     * method in client thread class for this client
     */
    public static void startThread(){
        try{
            ct = new ClientThread(s);
            Thread ts = new Thread(ct);
            ts.start();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to add new friend
     * @param name name of friends to be added
     */
    public static void addFriend(String name){
        try{
            ou.writeUTF("ADD " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to delete friend
     * @param user friends to be deleted
     */
    public static void deleteFriend(String user){
        try{
            ou.writeUTF("DELETEFRIEND " + user);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to block other user
     * @param name friends to be blocked
     */
    public static void blockUser(String name){
        try{
            ou.writeUTF("BLOCK " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to unblock other user
     * @param name friends to be unblocked
     */
    public static void unblockUser(String name){
        try{
            ou.writeUTF("UNBLOCK " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to search a friend to be added
     * @param name friends to be searched
     */
    public static void userSearch(String name){
        try{
            ou.writeUTF("SEARCH " + name);
            ou.flush();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to send request that client wants to sign in
     * @param name username to be checked
     * @param pass password to be checked
     */
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
    
    /**
     * this method is used to send request that client wants to register
     * @param name username to be registered
     * @param password password to be registered
     */
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
