/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Database.DatabaseFunction;
import java.util.Timer;
import java.util.TimerTask;
import network.ChatServer;

/**
 *
 * @author Lenovo
 */
public class ServerDriver {
    
    public static void main(String[] args) {
        DatabaseFunction dbf = DatabaseFunction.getInstance();
        dbf.Connect();
        ChatServer s = new ChatServer();
        s.startServer();
        s.check();
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("AYY");
//                try{
//                    Thread.sleep(2000);
//                }catch(InterruptedException e){
//                    System.out.println(e.getMessage());
//                }
//                System.out.println("LMAO");
//            }
//        });
//        t.start();
    }
    
}
