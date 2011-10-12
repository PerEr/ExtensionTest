package com.example.prototype.widgetloader;

import com.example.prototype.widgetloader.view.TestFrame;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new TestFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
