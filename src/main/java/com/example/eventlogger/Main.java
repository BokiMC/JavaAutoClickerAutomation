package com.example.eventlogger;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;



public class Main {
    public static void main(String[] args) throws AWTException {
        // Create a simple Swing window
        SwingUtilities.invokeLater(() -> {
            
            JFrame frame = new JFrame("Event Logger");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 500);

            JLabel label = new JLabel("Mouse position will be shown here.", SwingConstants.CENTER);
            frame.add(label, BorderLayout.CENTER);


            javax.swing.JButton button = new javax.swing.JButton("Start Listening");
            frame.add(button, BorderLayout.SOUTH);

            button.addActionListener(e -> {
                try {
                    Point location = MouseInfo.getPointerInfo().getLocation();
                    label.setText("Press Escape to replay.");
                    Recorder recorder = new Recorder();
                    recorder.startListening();
                } catch (Exception ex) {
                    label.setText("Error starting listener.");
                }
            });

            
            // Show mouse position on label
            try {
                Robot robot = new Robot();
                Point location = MouseInfo.getPointerInfo().getLocation();
                label.setText("Mouse is at: " + location.getX() + ", " + location.getY());
            } catch (AWTException e) {
                label.setText("Error getting mouse position.");
            }

            frame.setVisible(true);

            // Start recorder (if needed)
        });
    }
}