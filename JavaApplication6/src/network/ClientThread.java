/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.ChatHistoryHandler;
import GUI.FriendsForm;
import GUI.LoadingForm;
import GUI.LoginForm;
import com.AudioFile;
import com.Controller;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.net.Socket;
/**
 *
 * @author Lenovo
 */
public class ClientThread implements Runnable{
    
    private String input;
    private DataInputStream in;
    private String friendTemp;
    public static boolean loadingFlag;
    public volatile boolean flag = false;
    
    public ClientThread(Socket s) throws IOException{
        in = new DataInputStream(s.getInputStream());
        loadingFlag = false;
    }
    
    public void stop(){
        flag = true;
    }
    
    @Override
    public void run() {
        try{
            String array[];
            while(!false){
                input = ChatClient.in.readUTF();
                array = input.split(" ");
                System.out.println(input);
                if(input.startsWith("SIGNINRSLT ")){
                    
                    if(Boolean.parseBoolean(array[1])){
                        Controller.signInAccept();
                    }else{
                        JOptionPane.showMessageDialog(null, "Login Failed");
                        LoginForm.enableAll();
                    }
                    
                }else if(input.startsWith("SIGNINRSLTMSG ")){
                    
                    JOptionPane.showMessageDialog(null, input.substring(array[0].length()+1));
                    LoginForm.enableAll();
                    
                }else if(input.startsWith("SIGNUPRSLT ")){
                    
                    if(Boolean.parseBoolean(array[1])){
                      Controller.signUptoLogin();
                    }else{
                        JOptionPane.showMessageDialog(null, "Username is used");
                    }
                    
                }else if(input.startsWith("RECEIVEMSG ")){
                    
                    String msg = array[1] + ": "+input.substring(array[0].length() + array[1].length() + 2);
                    new Thread(new AudioFile()).start();
                    if(FriendsForm.getSelectedFList() == null){
                        ChatHistoryHandler.addHistory(array[1],  msg);
                        continue;
                    }
                    
                    if(FriendsForm.getSelectedFList().equals(array[1])){
                        FriendsForm.getMsg(array[1], msg);
                        ChatHistoryHandler.addHistory(array[1], msg);
                    }else{
                        FriendsForm.getMsg(array[1], msg);
                        ChatHistoryHandler.addHistory(array[1], msg);
                    }
                    
                }else if(input.startsWith("SRESULT ")){
                    
                    if(Boolean.parseBoolean(array[1]))
                        FriendsForm.searchBtn();
                    else
                        FriendsForm.showMessage("Username does not Exist");
                    
                }else if(input.startsWith("SADDED ")){
                    
                    if(Boolean.parseBoolean(array[1]))
                        FriendsForm.addFriend();
                    else
                        FriendsForm.showMessage("User already in friend list");
                
                }else if(input.startsWith("UPDATEFL ")){
                    
                    FriendsForm.addFriend(array[1]);
                
                }else if(input.startsWith("FILENOTIF ")){
                    
                    FriendsForm.getFTNotification(input.substring(array[0].length() + 3 + array[1].length() + array[2].length()), array[1]);
                    friendTemp = array[1];
                
                }else if(input.startsWith("FILECONFIRM ")){
                    
                    if(Boolean.parseBoolean(array[1]) && LoadingForm.loadingFlag /*&& LoadingForm.loadingFlag*/){
                        System.out.println("Sending Data");
                        LoadingForm.fileSentConfirm();
                        ChatClient.sendData(FriendsForm.getSelectedFList(), FriendsForm.fileName);
                    }else if(!Boolean.parseBoolean(array[1]) && LoadingForm.loadingFlag /*&& LoadingForm.loadingFlag*/){
                        LoadingForm.fileSentDecline();
                    }
                
                }else if(input.startsWith("SENDFILE ")){
                    
                    FileOutputStream fou = new FileOutputStream(new File("SentFiles\\" + input.substring(array[0].length() + 2 + array[1].length())));
                    byte arr[] = new byte[Integer.parseInt(array[1])];
                    in.readFully(arr);
                    fou.write(arr);
                    fou.close();
                    
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
