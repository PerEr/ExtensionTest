package app;

import app.view.AppFrame;

import javax.script.ScriptException;
import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame frame = new AppFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ScriptException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

}
