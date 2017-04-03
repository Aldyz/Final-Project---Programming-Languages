/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.Validator;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import network.ChatClient;

/**
 *
 * @author user
 */
public class ProfileOpt extends javax.swing.JDialog {

    /**
     * Creates new form ProfileOpt
     */
    public ProfileOpt(java.awt.Frame parent, boolean b) {
        super(parent, b);
        initComponents();
        lblUsername.setText(ChatClient.getName());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    ProfileOpt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPassword = new javax.swing.JLabel();
        lblRePassword = new javax.swing.JLabel();
        lblProfile = new javax.swing.JLabel();
        ChangePass = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        openFolder = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblPassword.setText("Password");

        lblRePassword.setText("Re-type Password");

        lblProfile.setText("PROFILE");

        ChangePass.setText("Change Password");
        ChangePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePassActionPerformed(evt);
            }
        });

        lblUser.setText("User");

        lblUsername.setText("jLabel7");

        openFolder.setText("Open Sent Folder");
        openFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFolderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPassword)
                            .addComponent(lblRePassword)
                            .addComponent(lblUser))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsername)
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPasswordField2))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(lblProfile))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openFolder)
                            .addComponent(ChangePass))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(lblUser))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRePassword)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(openFolder)
                .addGap(18, 18, 18)
                .addComponent(ChangePass)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFolderActionPerformed
        // TODO add your handling code here:
        try{
            Desktop.getDesktop().open(new File("C:\\Users\\Lenovo\\Documents\\Programming Languages\\Final Project\\Final-Project---Programming-Languages\\JavaApplication6\\SentFiles"));
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_openFolderActionPerformed

    private void ChangePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangePassActionPerformed
        // TODO add your handling code here:
        ChatClient.changePassword(jPasswordField1.getText());
        jPasswordField1.setText("");
        jPasswordField2.setText("");
        JOptionPane.showMessageDialog(null, "Password Updated");
        String newPass = jPasswordField1.getText();
        if(Validator.isEmpty(newPass)){
            JOptionPane.showMessageDialog(this, "Don't leave any boxes empty");
            return;
        }
        
        if(Validator.containsSpace(newPass)){
            JOptionPane.showMessageDialog(this, "Don't use whitespace is any of the boxes.");
            return;
        }
        ChatClient.changePassword(jPasswordField1.getText());
    }//GEN-LAST:event_ChangePassActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChangePass;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblRePassword;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JButton openFolder;
    // End of variables declaration//GEN-END:variables
}
