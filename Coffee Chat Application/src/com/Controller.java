/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import GUI.FriendsForm;
import GUI.LoginForm;
import GUI.SignUpForm;
import network.ChatClient;

/**
 * this class is used for managing the UI and connect the client to the server
 * @author Aldi, Vero, Vincent
 */
public class Controller {
    
    static FriendsForm ff;
    static LoginForm lf;
    static SignUpForm suf;
    
    /**
     * this constructor is used to set the application theme
     * sets the client's ip
     * connect the client to the server
     * show the login form
     * start the client thread
     */
    public Controller(){
        setLooksandFeel();
        ChatClient.setIP();
        ChatClient.Connect();
        lf = new LoginForm();
        ChatClient.startThread();
    }
    
    /**
     * this method is used to set the client's name
     * dispose the login form and show create new friends form
     */
    public static void signInAccept(){
        ChatClient.setName(LoginForm.txtName.getText());
        lf.dispose();
        ff = new FriendsForm();
    }
    
    /**
     * this method is used when the user log out
     * to dispose the friends form and go back to the login form
     */
    public static void logOut(){
        ff.dispose();
        lf = new LoginForm();
    }
    
    /**
     * this method is used to dispose the login form and show the sign up form
     */
    public static void signUp(){
        lf.dispose();
        suf = new SignUpForm();
    }
    
    /**
     * this method is used to dispose the sign up form and show the login form
     */
    public static void signUptoLogin(){
        suf.dispose();
        lf = new LoginForm();
    }
    
    /**
     * this method is used to set the looks and feel ( set the theme of the app )
     */
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
