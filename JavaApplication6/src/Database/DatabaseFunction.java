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

/**
 * this class is used to Handles the functionality and the content of the 
 * database, such as manipulating each row’s contents.
 * @author Aldi, Vero, Vincent
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
    
    //default constructor
    private DatabaseFunction(){
        
    }
    
    /**
     * this method is used to create new database function object
     * @return new database function object
     */
    public static DatabaseFunction getInstance(){
        return new DatabaseFunction();
    }
    
    /**
     * this method is to refresh the result set content from database
     */
    public static void refresh(){
        try{
            rs = stmt.executeQuery(query);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to insert new name, password, and email to the server database
     * @param name new user's name that has signed up
     * @param password new user's password that has signed up
     */
    public static void Insert(String name, String password)
    {
            try
            {
                String sAction;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dt = new Date();
                String currentDate = dateFormat.format(dt);
                sAction = "INSERT INTO USERSDATA (NAME, PASSWORD, SIGNUP) VALUES ('" + name + "', '" + password + "', '"+ currentDate + "')";
                stmt.executeUpdate(sAction);
                refresh();
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        
    }
    
    /**
     * this method is used to update the data in database
     * @param password new user's password
     * @param name user's name
     */
    public static void Update(String password, String name)
    {   
        try
        {
                stmt.executeUpdate("UPDATE usersdata SET PASSWORD = '" + password + "' WHERE NAME = '" + name + "'");
                refresh();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to close the connection to the database
     */
    public void close(){
        try{
            rs.close();
            stmt.close();
            connect.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * this method is used to create a new connection to the database and get the data
     */
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
    
    /**
     * this method is used to check the data of the user that wants to create a new account
     * @param name name of the client that wants to create a new account
     * @return boolean true when there is no matching name
     */
    public static boolean SignUpChecker(String name){
        
        try{
            PreparedStatement ps = connect.prepareStatement(query + " WHERE name = ?");
            ps.setString(1, name);
            ResultSet temp = ps.executeQuery();
            while(temp.next()){
                if(temp.getString("name").equals(name))
                    return false;
            }
            temp.close();
            ps.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return true;
    }
    
    /**
     * this method is used to check the name and password of user when the user is trying to be logged in
     * @param name user's name
     * @param pass user's password
     * @return boolean true when the name and password are true
     */
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
            temp.close();
            ps.close();
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());;
        }
        
        return false;
    }
    
    /**
     * this method is used in client Handler class to search friends that wants to be added
     * @param name the name of friends to be added
     * @return boolean true when the name exist
     */
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
            temp.close();
            ps.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
