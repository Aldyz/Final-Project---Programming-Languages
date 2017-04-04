/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import Database.DatabaseFunction;
import network.ChatServer;

/**
 * This class is used to start the server and allow the client to connect to the server
 * @author Aldi, Vero, Vincent
 */
public class ServerDriver {
    
    public static void main(String[] args) {
        DatabaseFunction dbf = DatabaseFunction.getInstance();
        dbf.Connect();
        ChatServer s = new ChatServer();
        s.startServer();
        s.check();
    }
    
}
