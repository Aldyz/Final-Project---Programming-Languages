/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.ChatHistoryHandler;
import GUI.FriendsForm;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.net.Socket;
/**
 *
 * @author Lenovo
 */
public class ClientThread implements Runnable{
    
    String input;
    static DataInputStream in;
    
    public ClientThread(Socket s) throws IOException{
        in = new DataInputStream(s.getInputStream());
    }
    
    @Override
    public void run() {
        try{
            String array[];
           while(true){
                input = ChatClient.in.readUTF();
                array = input.split(" ");
                if(input.startsWith("RECEIVEMSG")){
                    String msg = array[1] + ": "+input.substring(array[0].length() + array[1].length() + 2);
                    if(FriendsForm.getSelectedFList() == null){
                        ChatHistoryHandler.addHistory(array[1],  msg);
                        continue;
                    }
                    if(FriendsForm.getSelectedFList().equals(array[1])){
                        FriendsForm.getMsg(msg);
                        ChatHistoryHandler.addHistory(array[1], msg);
                    }else{
                        ChatHistoryHandler.addHistory(array[1], msg);
                    }
                }else if(input.startsWith("SRESULT")){
                    if(Boolean.parseBoolean(array[1]))
                        FriendsForm.searchBtn();
                    else
                        FriendsForm.showMessage("Username does not Exist");
                }else if(input.startsWith("SADDED")){
                    if(Boolean.parseBoolean(array[1]))
                        FriendsForm.addFriend();
                    else
                        FriendsForm.showMessage("User already in friend list");
                }else if(input.startsWith("UPDATEFL")){
                    FriendsForm.addFriend(array[1]);
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
