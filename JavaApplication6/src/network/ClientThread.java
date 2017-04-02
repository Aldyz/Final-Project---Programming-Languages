/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Database.ChatHistoryHandler;
import Database.GroupHistoryHandler;
import GUI.FriendsForm;
import GUI.LoadingForm;
import com.AudioFile;
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
    
    public ClientThread(Socket s) throws IOException{
        in = new DataInputStream(s.getInputStream());
        loadingFlag = false;
    }
    
    @Override
    public void run() {
        try{
            String array[];
            while(true){
                input = ChatClient.in.readUTF();
                array = input.split(" ");
                System.out.println(input);
                if(input.startsWith("RECEIVEMSG")){
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
                }else if(input.startsWith("CREATGRUPRSLT")){
                    System.out.println(Boolean.parseBoolean(array[1]));
                    if(Boolean.parseBoolean(array[1]))
                        FriendsForm.addGroup();
                    else
                        FriendsForm.showMessage("Group name already used");
                }else if(input.startsWith("GRUPRMVD")){
                    
                }else if(input.startsWith("GROUPINVITE")){
                    
                }else if(input.startsWith("GRUPMSG")){
                    String msg = array[1] + ": " + input.substring(array[0].length() + array[1].length() + 3 + array[2].length());
                    new Thread(new AudioFile()).start();
                    if(FriendsForm.getSelectedGList() == null){
                        GroupHistoryHandler.addHistory(array[2],  msg);
                        continue;
                    }
                    
                    FriendsForm.getGrpMsg(array[2], msg);
                    GroupHistoryHandler.addHistory(array[2], msg);
                }else if(input.startsWith("FILENOTIF")){
                    FriendsForm.getFTNotification(input.substring(array[0].length() + 3 + array[1].length() + array[2].length()), array[1]);
                    friendTemp = array[1];
                }else if(input.startsWith("FILECONFIRM")){
                    if(Boolean.parseBoolean(array[1]) && LoadingForm.loadingFlag /*&& LoadingForm.loadingFlag*/){
                        System.out.println("Sending Data");
                        LoadingForm.fileSentConfirm();
                        ChatClient.sendData(ChatClient.getName(), FriendsForm.fileName);
                    }else if(!Boolean.parseBoolean(array[1]) && LoadingForm.loadingFlag /*&& LoadingForm.loadingFlag*/){
                        LoadingForm.fileSentDecline();
                    }
                }else if(input.startsWith("SENDFILE")){
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
