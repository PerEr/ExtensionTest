package app.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class AppFrame extends Frame {

    private Image m_img;

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);    //To change body of overridden methods use File | Settings | File Templates.
        Insets insets = getInsets();
        graphics.drawImage(m_img, insets.left, insets.top, this);
    }

    public AppFrame() {


        ClassLoader ld = ClassLoader.getSystemClassLoader();
        URL url = ld.getResource("background.png");
        Toolkit tk = Toolkit.getDefaultToolkit();
        m_img = tk.getImage(url);

        setSize(1024, 563);

        show();

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
}
