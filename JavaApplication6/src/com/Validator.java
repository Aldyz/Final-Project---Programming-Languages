/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 * This class is used to check whether the user's input are right or worng
 * @author Aldi, Vero, Vincent
 */
public class Validator {
    
    /**
     * this method is used to validate the username
     * @param name the client's username
     * @return 
     */
    public static boolean UserNameValidate(String name){
        char array[] = name.toCharArray();
        for(int i = 0; i<name.length(); i++){
            if((int)array[i]>=65 && (int)array[i]<=90)
                continue;
            else if((int)array[i]>=97 && (int)array[i]<=122)
                continue;
            else if((int)array[i]>=48 && (int)array[i]<=57)
                continue;
            else
                return false;
        }
        return true;
    }
    
    /**
     * this method is used to check the length of user name and password
     * @param word string to be counted
     * @param count maximum characters that are allowed
     * @return boolean true if the specification is satisfied
     */
    public static boolean isOverCharLimit(String word, int count){
        if(word.length()>count){
            return true;
        }else
            return false;
    }
    
    public static boolean containsSpace(String word){
        if(word.contains(" "))
            return true;
        else
            return false;
    }
    
    public static boolean isEmpty(String word){
        if(word.trim().length() == 0)
            return true;
        else
            return false;
    }
    
}
