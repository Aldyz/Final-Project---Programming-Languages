/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Database.ChatHistoryHandler;
import com.AudioFile;
import com.Controller;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import network.ChatClient;
import com.Validator;
import java.io.InputStream;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.*;

/**
 * This class is used as the main feature (chatting application) 
 * and provide several features
 * @author Aldi, Vero, Vincent
 */
public class FriendsForm extends javax.swing.JFrame {

    public static String friendAdd;
    public static String fileName;
    public static DefaultListModel<String> friendsList;
    public static DefaultListModel<String> notificationList;
    
    /**
     * Creates new form FriendsForm
     */
    public FriendsForm() {
        initComponents();
        reset();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("af03fc6642fcd5210a3af609bcaeca2e.png")));
        new BlockList();
        setImage();
        setList();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * this method is used to disable all the button and text field
     */
    public void disableAll(){
        txtChatField.setEnabled(false);
        btnBlock.setEnabled(false);
        bttDltChat.setEnabled(false);
        btnUnblock.setEnabled(false);
        btnAddFile.setEnabled(false);
        btnSend.setEnabled(false);
    }
    
    /**
     * this method is used to get friend list and block list from the server 
     * and set it for later use
     */
    public void setList(){
        String array[];
        String array2[];
        String array3[];
        friendsList = new DefaultListModel<String>();
        notificationList = new DefaultListModel<String>();
        JFriendList.setModel(friendsList);
        jNotifList.setModel(notificationList);
        array = ChatClient.getFriendList().split(" ");
        for(int i = 0; i < array.length; i++){
            friendsList.addElement(array[i]);
        }
        
        array2 = ChatClient.getBlockList().split(" ");
        for(int i = 0; i < array2.length; i++){
            BlockList.blocked.addElement(array2[i]);
        }
        
    }
    
    /**
     * this method is used to get image form the project src folder and set the 
     * logo in the add tab folder( add friends pane)
     */
    public void setImage(){
        
        try{
            BufferedImage img = null;
            URL url = getClass().getResource("./coffee-cup-clip-art-at-clker-coffee-clipart-600_519.png");
            img = ImageIO.read(url);
            Image image = img.getScaledInstance(lblAddPhoto.getWidth(), lblAddPhoto.getHeight(), Image.SCALE_SMOOTH);
            lblAddPhoto.setIcon(new ImageIcon(image));
            lblAddPhoto.revalidate();
            lblAddPhoto.repaint();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
    }
    
    /**
     * This method is used to reset the forms to the original condition
     */
    public static void reset(){
        btnAdd.setEnabled(false);
        lblName.setText("");
    }
    
    /**
     * this method is used to append the message in the chat text field 
     * and notify the friend that recieve the message
     * @param name name of the message sender
     * @param msg message that is sent
     */
    public static void getMsg(String name,String msg){
        if(!JFriendList.getSelectedValue().startsWith(name)){
            for(int i = 0; i < friendsList.size(); i++){
                if(friendsList.get(i).startsWith(name)){
                    friendsList.remove(i);
                    friendsList.add(0, name + " *");
                }
            }
        }else{
            ChatText.append(msg);
        }
    }
    
    /**
     * enable all the button and text field in friends form 
     */
    public void enableAll(){
        ChatText.setEnabled(true);
        txtChatField.setEnabled(true);
        btnBlock.setEnabled(true);
        bttDltChat.setEnabled(true);
        btnUnblock.setEnabled(true);
        btnProfile.setEnabled(true);
        btnLogout.setEnabled(true);
        btnAddFile.setEnabled(true);
        btnSend.setEnabled(true);
    }
    
    /**
     * this method is used to get notification when a file is recieved
     * @param name name of the file
     * @param sender name of the sender
     */
    public static void getFTNotification(String name, String sender){
        notificationList.addElement("File Transfer Request: " + name + ", From: " + sender);
    }
    
    /**
     * this method is used to get messages from the client and append it to the
     * client chat text field then set the txt field back to empty string
     * then the messages that are going to be sent added to client's chat history
     * the messages are also sent to the other client
     */
    public void sendMsg(){
        String msg =  txtChatField.getText() + "\n";
        if(msg.equals(""))
            return;
        ChatText.append(ChatClient.getName() + ": " +msg);
        txtChatField.setText("");
        if(!JFriendList.getSelectedValue().contains("*"))
            ChatHistoryHandler.addHistory(JFriendList.getSelectedValue(), ChatClient.getName() + ": " +msg);
        else
            ChatHistoryHandler.addHistory(JFriendList.getSelectedValue().split(" ")[0], ChatClient.getName() + ": " +msg);
        ChatClient.sendMsg(JFriendList.getSelectedValue(), msg);
    }
    
    /**
     * @return  the name of selected friend from jFriendList
     */
    public static String getSelectedFList(){
        return JFriendList.getSelectedValue();
    }
    
    /**
     * this method is used to show message in FriendsForm when the username 
     * does not exist or the user already in friend list
     * @param msg the error message that is passed
     */
    public static void showMessage(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
    
    /**
     * this method is used to set the name of friends that want to be added if 
     * the users exist and set he add button to enable
     */
    public static void searchBtn(){
        lblName.setText(friendAdd);
        btnAdd.setEnabled(true);
    }
    
    /**
     * this method is used to add friend to default list model and create a new 
     * file for the new friend's chat history
     * @param name name of the friend
     */
    public static void addFriend(String name){
        if(friendsList.size()>100){
            JOptionPane.showMessageDialog(null, "Friend List is already over limit.");
        }else{
            friendsList.addElement(name);
            JFriendList.setModel(friendsList);
            ChatHistoryHandler.createHistory(name);
        }
    }
   
    /**
     * this method is used to add friend to default list model and create new 
     * file for the new friend's chat history
     */
    public static void addFriend(){
        friendsList.addElement(friendAdd);
        JFriendList.setModel(friendsList);
        ChatHistoryHandler.createHistory(friendAdd);
        reset();
        JOptionPane.showMessageDialog(null, friendAdd + " has been added.");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneLeft = new javax.swing.JPanel();
        tabGroup = new javax.swing.JTabbedPane();
        paneFriend = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JFriendList = new javax.swing.JList<>();
        btnBlock = new javax.swing.JButton();
        bttDltChat = new javax.swing.JButton();
        btnUnblock = new javax.swing.JButton();
        paneAddFriend = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        lblAddPhoto = new javax.swing.JLabel();
        paneNotif = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jNotifList = new javax.swing.JList<>();
        paneRight = new javax.swing.JPanel();
        txtChatField = new javax.swing.JTextField();
        btnAddFile = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatText = new javax.swing.JTextArea();
        btnProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tabGroup.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JFriendList.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        JFriendList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JFriendList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFriendListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JFriendList);

        btnBlock.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnBlock.setText("Block");
        btnBlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBlockActionPerformed(evt);
            }
        });

        bttDltChat.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        bttDltChat.setText("Delete Chat");
        bttDltChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttDltChatActionPerformed(evt);
            }
        });

        btnUnblock.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnUnblock.setText("Unblock");
        btnUnblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnblockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneFriendLayout = new javax.swing.GroupLayout(paneFriend);
        paneFriend.setLayout(paneFriendLayout);
        paneFriendLayout.setHorizontalGroup(
            paneFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
            .addGroup(paneFriendLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(btnBlock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(bttDltChat)
                .addGap(100, 100, 100)
                .addComponent(btnUnblock)
                .addGap(38, 38, 38))
        );
        paneFriendLayout.setVerticalGroup(
            paneFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneFriendLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBlock)
                    .addComponent(btnUnblock)
                    .addComponent(bttDltChat))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        tabGroup.addTab("Friend List", paneFriend);

        paneAddFriend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        paneAddFriend.setForeground(new java.awt.Color(153, 102, 0));

        lblId.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        lblId.setText("Username:");

        txtId.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N

        btnSearch.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout paneAddFriendLayout = new javax.swing.GroupLayout(paneAddFriend);
        paneAddFriend.setLayout(paneAddFriendLayout);
        paneAddFriendLayout.setHorizontalGroup(
            paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneAddFriendLayout.createSequentialGroup()
                .addGroup(paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneAddFriendLayout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addGroup(paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneAddFriendLayout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneAddFriendLayout.createSequentialGroup()
                                .addComponent(lblId)
                                .addGap(18, 18, 18)
                                .addGroup(paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneAddFriendLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(lblAddPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paneAddFriendLayout.setVerticalGroup(
            paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneAddFriendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAddPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addGroup(paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneAddFriendLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(lblName))
                    .addGroup(paneAddFriendLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(paneAddFriendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch)
                        .addGap(101, 101, 101)
                        .addComponent(btnAdd)))
                .addGap(118, 118, 118))
        );

        tabGroup.addTab("Add Friend", paneAddFriend);

        jNotifList.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jNotifList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jNotifList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jNotifListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jNotifList);

        javax.swing.GroupLayout paneNotifLayout = new javax.swing.GroupLayout(paneNotif);
        paneNotif.setLayout(paneNotifLayout);
        paneNotifLayout.setHorizontalGroup(
            paneNotifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
        );
        paneNotifLayout.setVerticalGroup(
            paneNotifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
        );

        tabGroup.addTab("Notifications", paneNotif);

        javax.swing.GroupLayout paneLeftLayout = new javax.swing.GroupLayout(paneLeft);
        paneLeft.setLayout(paneLeftLayout);
        paneLeftLayout.setHorizontalGroup(
            paneLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneLeftLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        paneLeftLayout.setVerticalGroup(
            paneLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneLeftLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtChatField.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        txtChatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChatFieldActionPerformed(evt);
            }
        });

        btnAddFile.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnAddFile.setText("Add File");
        btnAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFileActionPerformed(evt);
            }
        });

        btnSend.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        ChatText.setEditable(false);
        ChatText.setColumns(20);
        ChatText.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        ChatText.setRows(5);
        jScrollPane1.setViewportView(ChatText);

        btnProfile.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnProfile.setText("Profile");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Monotype Corsiva", 0, 18)); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneRightLayout = new javax.swing.GroupLayout(paneRight);
        paneRight.setLayout(paneRightLayout);
        paneRightLayout.setHorizontalGroup(
            paneRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneRightLayout.createSequentialGroup()
                .addComponent(txtChatField, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 5, Short.MAX_VALUE))
            .addGroup(paneRightLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(paneRightLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(71, 71, 71))
        );
        paneRightLayout.setVerticalGroup(
            paneRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneRightLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paneRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProfile)
                    .addComponent(btnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(paneRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtChatField, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneRightLayout.createSequentialGroup()
                        .addComponent(btnAddFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSend)))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(paneLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(paneRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paneRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtChatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChatFieldActionPerformed
        // TODO add your handling code here:
            sendMsg();
    }//GEN-LAST:event_txtChatFieldActionPerformed

    /**
     * this method is used to show file chooser and lets the client choose a file
     * @param evt clicked mouse input
     */
    private void btnAddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFileActionPerformed
        try{
            // TODO add your handling code here:
            //when the photo label is clicked, new file menu is created
            JFileChooser fc = new JFileChooser();

            //set all the file that can be accepted to true
            fc.setAcceptAllFileFilterUsed(true);

        
            FileInputStream selectedFile;
            //if the user click open
            if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                selectedFile = new FileInputStream(fc.getSelectedFile());
                fileName = fc.getSelectedFile().getName();
                int l = selectedFile.available();
                if(l>10000000){
                    JOptionPane.showMessageDialog(this, "Maximum File Size 10 Mb");
                    return;
                }
                ChatClient.arr = new byte[l];
                selectedFile.read(ChatClient.arr);
                ChatClient.fileSender(fileName, JFriendList.getSelectedValue(), l);
                LoadingForm.loadingFlag = true;
                new LoadingForm(this, true);
                
            }else{
                JOptionPane.showMessageDialog(this, "No file was chosen");
            }   
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnAddFileActionPerformed

    /**
     * this method is used to set the chat field using the chat history's file
     * from the server when a friend is selected
     * @param evt clicked mouse input
     */
    private void JFriendListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JFriendListMouseClicked
        // TODO add your handling code here:
        if(JFriendList.getSelectedValue().equals("") || JFriendList.getSelectedValue() == null){
            return;
        }
        
        ChatText.setEnabled(true);
        int i = JFriendList.getSelectedIndex();
        String word = friendsList.get(i).substring(0,  friendsList.get(i).length()-2);
        if(JFriendList.getSelectedValue().contains("*")){
            ChatText.setText(ChatHistoryHandler.getHistory(word));
            friendsList.remove(i);
            friendsList.add(i, word);
            JFriendList.setSelectedIndex(i);
        }else{
            ChatText.setText(ChatHistoryHandler.getHistory(JFriendList.getSelectedValue()));
        }
    }//GEN-LAST:event_JFriendListMouseClicked

    /**
     * this method is used to add the friend that have been searched
     * @param evt clicked mouse input
     */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        ChatClient.addFriend(friendAdd);
    }//GEN-LAST:event_btnAddActionPerformed

    /**
     * this method is used to block the selected user from jFriendList and ask 
     * whether the client really wan tot block it or not
     * if yes, then the user will be blocked and removed from the friend list
     * @param evt clicked mouse input
     */
    private void btnBlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBlockActionPerformed
        // TODO add your handling code here:
        if(JFriendList.getSelectedValue() == null)
            return;
        
        int confirm =JOptionPane.showConfirmDialog(this, "Are you sure you want to block this user?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
            ChatClient.blockUser(JFriendList.getSelectedValue());
            BlockList.blocked.addElement(JFriendList.getSelectedValue());
            friendsList.removeElement(JFriendList.getSelectedValue());
            JFriendList.setModel(friendsList);
            ChatText.setText("");
        }
    }//GEN-LAST:event_btnBlockActionPerformed

    /**
     * this method is used to search friend and if the searched one is ourselves
     * or have been added or if you do not search anything it will show jOptionPan 
     * @param evt clicked mouse input
     */
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        friendAdd = txtId.getText();
        
        if(friendAdd.equals(ChatClient.getName())){
            JOptionPane.showMessageDialog(this, "User");
            return;
        }
        
        if(friendsList.contains(friendAdd)){
            JOptionPane.showMessageDialog(this, "User already in friend list");
            return;
        }
        
        if(friendAdd.trim().length() == 0){
            JOptionPane.showMessageDialog(this, "Don't Leave Search Box Empty");
            return;
        }
        ChatClient.userSearch(txtId.getText());
        
    }//GEN-LAST:event_btnSearchActionPerformed

    /**
     * this method is used to show ProfileOpt frame
     * @param evt clicked mouse input
     */
    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        // TODO add your handling code here:
        new ProfileOpt(this, true);
    }//GEN-LAST:event_btnProfileActionPerformed

    /**
     * this method is used to send message
     * @param evt clicked mouse input
     */
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
            sendMsg();
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * this method is used to delete chat history from database and the chat area
     * @param evt clicked mouse input
     */
    private void bttDltChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttDltChatActionPerformed
        //  TODO add your handling code here:
        ChatHistoryHandler.clearHistory(JFriendList.getSelectedValue());
        ChatText.setText("");
    }//GEN-LAST:event_bttDltChatActionPerformed

    /**
     * this method is used to show the block list of friend that can be selected
     * @param evt clicked mouse input
     */
    private void btnUnblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnblockActionPerformed
        // TODO add your handling code here:
        new BlockList(this, true);
    }//GEN-LAST:event_btnUnblockActionPerformed

    /**
     * this method allow the client to get the file sent from other user
     * @param evt clicked mouse input
     */
    private void jNotifListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jNotifListMouseClicked
        // TODO add your handling code here:
        String array[] = jNotifList.getSelectedValue().split(" ");
        if(jNotifList.getSelectedValue().startsWith("File Transfer")){
           if(JOptionPane.showConfirmDialog(this, "Confirm Request", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
               ChatClient.fileSendConfirm(array[array.length-1], true);
           }else{
               ChatClient.fileSendConfirm(array[array.length-1], false);
           }
        }
        
        notificationList.remove(jNotifList.getSelectedIndex());
        enableAll();
    }//GEN-LAST:event_jNotifListMouseClicked

    /**
     * this method is used to log out from the chat app and go back to the login form
     * @param evt clicked mouse input
     */
    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        ChatClient.logOut();
        Controller.logOut();
    }//GEN-LAST:event_btnLogoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextArea ChatText;
    private static javax.swing.JList<String> JFriendList;
    private static javax.swing.JButton btnAdd;
    private static javax.swing.JButton btnAddFile;
    private static javax.swing.JButton btnBlock;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private static javax.swing.JButton btnSearch;
    private static javax.swing.JButton btnSend;
    private javax.swing.JButton btnUnblock;
    private javax.swing.JButton bttDltChat;
    private javax.swing.JList<String> jNotifList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddPhoto;
    private static javax.swing.JLabel lblId;
    private static javax.swing.JLabel lblName;
    private static javax.swing.JPanel paneAddFriend;
    private javax.swing.JPanel paneFriend;
    private javax.swing.JPanel paneLeft;
    private javax.swing.JPanel paneNotif;
    private javax.swing.JPanel paneRight;
    private javax.swing.JTabbedPane tabGroup;
    private static javax.swing.JTextField txtChatField;
    private static javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
