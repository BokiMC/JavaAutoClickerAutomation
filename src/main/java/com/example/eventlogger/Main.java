package com.example.eventlogger;
import java.awt.*;
import java.io.BufferedReader;
public class Main {
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        Point location = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Mouse is at: " + location.getX() + ", " + location.getY());
        Recorder recorder = new Recorder();
        recorder.startListening();
    
    }
}