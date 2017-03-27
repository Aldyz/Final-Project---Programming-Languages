/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.IOException;
import javax.swing.JOptionPane;
import java.net.Socket;
/**
 *
 * @author Lenovo
 */
public class ClientThread implements Runnable{
    
    String input;
    
    @Override
    public void run() {
        try{
            while(true){
                input = ChatClient.in.readUTF();
                
                if(input.startsWith("RECEIVEMSG")){
                    
                }
                Thread.sleep(1000);
            }
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    
}
