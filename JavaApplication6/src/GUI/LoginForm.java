/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.Utilities;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import network.ChatClient;

/*
   @author Aldi, Vero, Vincent
*/
public class LoginForm extends javax.swing.JFrame {
    
    //to create IP Address window
    private JFrame jAddress = new JFrame();
    //to hold the IP Address
    private static String ipAddress;
    //to hold the Logo Path
    
     //Creates new LoginForm
    public LoginForm() {
        
        initComponents();
        setImage();
        setRememberMe();
        //sets the program on the centre
        this.setLocationRelativeTo(null);
        
        //set the default close operation
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    
    public void setRememberMe(){
        String array[] = Utilities.getRememberMe();
        txtName.setText(array[0]);
        fldPassword.setText(array[1]);
        if(!array[0].equals("") && !array[1].equals(""))
            chBoxRemember.setSelected(true);
    }
    
    public void setImage(){
        //setting the logo
        try{
            BufferedImage img = null;
            URL url = getClass().getResource("./Coffee.png");
            img = ImageIO.read(url);
            Image image = img.getScaledInstance(lblPic.getWidth(), lblPic.getHeight(), Image.SCALE_SMOOTH);
            lblPic.setIcon(new ImageIcon(image));
            lblPic.revalidate();
            lblPic.repaint();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogin = new javax.swing.JButton();
        btnSignUp = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        chBoxRemember = new javax.swing.JCheckBox();
        btnIp = new javax.swing.JButton();
        lblPic = new javax.swing.JLabel();
        fldPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(240, 240));
        setResizable(false);

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnSignUp.setText("SignUp");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblUsername.setText("UserName");

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblPassword.setText("Password");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        chBoxRemember.setText("Remember me");

        btnIp.setText("IP Address");
        btnIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(118, 118, 118)
                            .addComponent(btnIp))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblPassword)
                                        .addComponent(lblUsername))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(chBoxRemember)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(17, 17, 17)
                                    .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPic, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chBoxRemember)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnIp)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // if the login failed
        
        boolean check = ChatClient.getAuthentication(txtName.getText(), fldPassword.getText());
        
        if(check){
            if(chBoxRemember.isSelected() && check){
                Utilities.RememberLogin(txtName.getText(), fldPassword.getText());
            }else{
                Utilities.RememberLogin("", "");
            }
            ChatClient.startThread();
            new FriendsForm();
            this.dispose();
        }else{
        JOptionPane.showMessageDialog(this, "Login Failed");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        //exit from the program
        new SignUpForm();
        this.dispose();
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIpActionPerformed
        //Show new window to input the IP and get the IP Addess
        
        ChatClient.setIP();
        ChatClient.Connect();
    }//GEN-LAST:event_btnIpActionPerformed


    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
//                   
//            
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new LoginForm().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIp;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JCheckBox chBoxRemember;
    private static javax.swing.JPasswordField fldPassword;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPic;
    private javax.swing.JLabel lblUsername;
    private static javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
