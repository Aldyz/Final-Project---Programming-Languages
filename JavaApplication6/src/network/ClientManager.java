/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.Socket;
/**
 *
 * @author Lenovo
 */
public class ClientManager implements Runnable{

    Socket s;
    
    public ClientManager(Socket s){
        this.s = s;
    }
    
    @Override
    public void run() {
        
    }
    
    
}
