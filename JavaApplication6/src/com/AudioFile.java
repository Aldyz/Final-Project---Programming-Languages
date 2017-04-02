/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.*;
import sun.audio.*;

/**
 *
 * @author Lenovo
 */
public class AudioFile implements Runnable{

    @Override
    public void run() {
        try{
            InputStream infile = new FileInputStream(new File("C:\\Users\\Lenovo\\Documents\\Programming Languages\\Final Project\\Final-Project---Programming-Languages\\JavaApplication6\\src\\com\\sms-alert-1-daniel_simon.wav"));

            // create an audiostream from the inputstream 
            AudioStream audioStream = new AudioStream(infile);

            // play the audio clip with the audioplayer class 
            AudioPlayer.player.start(audioStream); 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
       

}
