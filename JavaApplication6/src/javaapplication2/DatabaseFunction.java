/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Vincent
 */
public class DatabaseFunction 
{
    private static String sURL = "jdbc:mysql://localhost:3306/finalprojectoop?zeroDateTimeBehavior=convertToNull";
    private static String sUser = "root";
    private static String sPassword = "";
    private static Statement stmt;
    private static ResultSet rs;
    private static int nCurrentRow = 0;
    private static int nCounter = 0;
    private static Connection connect;
    private static String query = "Select * from usersdata";
    
    public void Insert(String name)
    {
        String sID = "";//TxtID.getText();
        int newID = 0;
        String sUserName = "";//TxtUserName.getText();
        String sPassword = "";//TxtPassword.getText();
        boolean bLogin = false;//CheckBoxTrue.getValue();
        
        try
        {
            newID = Integer.parseInt(sID);
            if(newID < 0)
            {
                int abs = Math.abs(newID);
                newID = abs;
            }
            
            try
            {
                String sAction;
                sAction = "INSERT INTO UNTITLED (IDNUMBER, USERNAME, PASSWORD, LOGINSTATE) VALUES ('" + newID + "', '" + sUserName + "', '" + sPassword + "', " + bLogin + ")";
                stmt.executeUpdate(sAction);
                //JOptionPane.showMessageDialog(this, "New prisoner has been added!");
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
                //JOptionPane.showMessageDialog(this, err.getMessage());
            }
        }
        catch(NumberFormatException err)
        {
            //JOptionPane.showMessageDialog(this, "Invalid input, please input an integer for ID!");
        }
    }
    
    protected void Update()
    {
        String sUserName = "";//TxtUserName.getText();
        String sPassword = "";//TxtPassword.getText();
        boolean bLogin = false;
        
        try
        {
                rs.updateString("USERNAME", sUserName);
                rs.updateString("PASSWORD", sPassword);
                rs.updateBoolean("LOGINSTATE", bLogin);
                rs.updateRow();
                //JOptionPane.showMessageDialog(this, "Prisoner's record updated!");
        }
        catch(SQLException err)
        {
           // JOptionPane.showMessageDialog(this, "Invalid input, please input an integer for ID!");
        }
    }
    
    protected void Delete()
    {
        try 
        {
            rs.deleteRow();
            nCurrentRow = rs.getRow() - 1;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void Connect()
    {   
        try 
        {
            connect = DriverManager.getConnection(sURL, sUser, sPassword);
            stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            query = "Select * from usersdata";
            rs = stmt.executeQuery(query);
            rs.first();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
            //Logger.getLogger(DatabaseFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected boolean SignInChecker(String name, String pass)
    {
        try 
        {
            PreparedStatement ps = connect.prepareStatement(query + " WHERE name = ? AND password = ?");
            ps.setString(1, name);
            ps.setString(2, pass);
            //String sql = ("SELECT * FROM USERDATA ORDER BY IDNUMBER DESC LIMIT 1;");
            return ps.executeQuery().first();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        DatabaseFunction dbf = new DatabaseFunction();
        dbf.Connect();
        dbf.SignInChecker("Aldi", "123");
    }
}
