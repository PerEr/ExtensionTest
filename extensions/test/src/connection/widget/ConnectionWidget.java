package connection.widget;

import api.widget.WidgetFactory;
import connection.api.ConnectionListener;
import connection.api.ConnectionMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

class ConnectionWidget extends JComponent {

    ConnectionWidget() {
        setOpaque(false);
        okPercentage = 0;
    }

    public ConnectionWidget(ConnectionMonitor connectionMonitor) {
        connectionMonitor.addListener(new ConnectionListener() {
            @Override
            public void onStateChange(int value) {
                okPercentage = value;
                repaint();
            }
        });

    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        int width = getWidth();
        int height = getHeight();
        Color color = Color.RED;
        if (okPercentage > 20)
            color = Color.ORANGE;
        if (okPercentage > 40)
            color = Color.PINK;
        if (okPercentage > 60)
            color = Color.YELLOW;
        if (okPercentage > 80)
            color = Color.GREEN;

        graphics.setColor(color);
        graphics.fillRect(0, height - height*okPercentage/100, 10, height*okPercentage/100);
    }

    static class Factory implements WidgetFactory {
        private ConnectionMonitor connectionMonitor;

        public Factory(ConnectionMonitor connectionMonitor) {
            this.connectionMonitor = connectionMonitor;
        }

        @Override
        public JComponent instantiate(Properties prp) {
            return new ConnectionWidget(connectionMonitor);
        }
    }

    private int okPercentage;
    final static String NAME = new String("connection.status");
}
