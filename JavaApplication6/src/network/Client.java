/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javaapplication2.*;

/**
 *
 * @author Lenovo
 */
public class Client {
    
    private static Socket s;
    private static DataInputStream in;
    private static DataOutputStream ou;
    private static boolean Authentication;
    
    public void Connect(){
        try{
            s = new Socket("192.168.31.19", 8000);
            ou = new DataOutputStream(s.getOutputStream());
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
