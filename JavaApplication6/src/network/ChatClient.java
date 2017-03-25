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
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class ChatClient {
    
    private static Socket s;
    private static DataInputStream in;
    private static DataOutputStream ou;
    private static boolean Authentication;
    private static String IP;
    
    public ChatClient(){
        IP = JOptionPane.showInputDialog("Input IP Address");
        System.out.println(IP);
    }
    
    public void Connect(){
        try{
            s = new Socket(IP, 8000);
            ou = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean getAuthentication(String name, String pass){
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
    
    
}
