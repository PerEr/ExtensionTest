package app;

import app.view.AppFrame;

import javax.swing.SwingUtilities;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AppFrame();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

}
