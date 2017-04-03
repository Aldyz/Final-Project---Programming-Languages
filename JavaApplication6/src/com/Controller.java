/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import GUI.FriendsForm;
import GUI.LoginForm;
import network.ChatClient;

/**
 *
 * @author Lenovo
 */
public class Controller {
    
    static FriendsForm ff;
    static LoginForm lf;
    
    public Controller(){
        setLooksandFeel();
        ChatClient c = ChatClient.getInstance();
        ChatClient.setIP();
        c.Connect();
        lf = new LoginForm();
        ChatClient.startThread();
    }
    
    public static void signInAccept(){
        ChatClient.setName(LoginForm.txtName.getText());
        lf.dispose();
        ff = new FriendsForm();
    }
    
    public static void logOut(){
        ff.dispose();
        lf = new LoginForm();
    }
    
    public void setLooksandFeel(){
        try {
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
                   
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
