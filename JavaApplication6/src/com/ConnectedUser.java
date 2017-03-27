/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ConnectedUser {
    
    private String Name;
    private Socket s;
    private DataInputStream in;
    private DataOutputStream ou;
    private ArrayList<String> Friends;
    private ArrayList<String> Groups;
    private ArrayList<String> Blocked;
    
    public ConnectedUser(String Name, Socket s){
        this.s = s;
        this.Name = Name;
        Friends = new ArrayList<String>();
        Groups = new ArrayList<String>();
        Blocked = new ArrayList<String>();
        try{
            in = new DataInputStream(s.getInputStream());
            ou = new DataOutputStream(s.getOutputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void setFriends(){
        
    }
    
    public void addFriend(){
        
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOu() {
        return ou;
    }

    public void setOu(DataOutputStream ou) {
        this.ou = ou;
    }
    
    
}
