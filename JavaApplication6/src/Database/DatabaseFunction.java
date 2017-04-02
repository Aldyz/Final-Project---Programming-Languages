/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Vincent
 */
public final class DatabaseFunction 
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
    
    private DatabaseFunction(){
        
    }
    
    public static DatabaseFunction getInstance(){
        return new DatabaseFunction();
    }
    
    public static void Insert(String name, String password, String email)
    {
        
        try
        {
            
            try
            {
                String sAction;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dt = new Date();
                String currentDate = dateFormat.format(dt);
                sAction = "INSERT INTO USERSDATA (NAME, PASSWORD, EMAIL, SIGNUP, LASTSIGNIN) VALUES ('" + name + "', '" + password + "', '" + email + "', '"+ currentDate + "', '" + currentDate +"')";
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
    
    protected static void Update()
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
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    protected static void Delete()
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
    
    public void close(){
        try{
            rs.close();
            stmt.close();
            connect.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void Connect()
    {   
        try 
        {
            connect = DriverManager.getConnection(sURL, sUser, sPassword);
            stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(query);
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static boolean SignUpChecker(String name, String email){
        
        try{
            PreparedStatement ps = connect.prepareStatement(query + " WHERE name = ? OR email = ?");
            ps.setString(1, name);
            ps.setString(2, email);
            ResultSet temp = ps.executeQuery();
            while(temp.next()){
                if(temp.getString("name").equals(name) || temp.getString("email").equals(email))
                    return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return true;
    }
    
    public static boolean SignInChecker(String name, String pass)
    {
        try 
        {
            PreparedStatement ps = connect.prepareStatement(query + " WHERE name = ? AND password = ?");
            ps.setString(1, name);
            ps.setString(2, pass);
            ResultSet temp = ps.executeQuery();
            while(temp.next()){
                if(temp.getString("name").equals(name) && temp.getString("password").equals(pass))
                    return true;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());;
        }
        
        return false;
    }
    
    public static boolean userCheck(String name){
        try{
            PreparedStatement ps = connect.prepareStatement(query + " WHERE name = ?");
            ps.setString(1, name);
            ResultSet temp = ps.executeQuery();
            while(temp.next()){
                if(temp.getString("name").equals(name)){
                    return true;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public static void main(String[] args) {
        DatabaseFunction dbf = new DatabaseFunction();
        dbf.Connect();
        System.out.println(dbf.SignInChecker("tio", "Hello"));
    }
}
