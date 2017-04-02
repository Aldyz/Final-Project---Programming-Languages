/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 *
 * @author USER
 */
public class Validator {
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
    
    public static boolean isOverCharLimit(String word){
        if(word.length()>30){
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
    
    public static void main(String[] args) {
        String let = "Aldo#";
        System.out.println(UserNameValidate(let));
    }
}
