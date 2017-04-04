/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.*;
import java.net.URL;
import sun.audio.*;

/**
 *
 * @author Lenovo
 */
public class AudioFile implements Runnable{

    @Override
    public void run() {
        try{
            InputStream infile = getClass().getResourceAsStream("sms-alert-1-daniel_simon.wav");

            // create an audiostream from the inputstream 
            AudioStream audioStream = new AudioStream(infile);

            // play the audio clip with the audioplayer class 
            AudioPlayer.player.start(audioStream); 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
       

}
