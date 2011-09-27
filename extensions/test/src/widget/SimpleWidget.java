package widget;


import javax.swing.*;
import java.awt.*;

public class SimpleWidget extends JPanel {
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.ORANGE);
        graphics.drawOval(10, 10, 40, 40);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100);
    }
}
