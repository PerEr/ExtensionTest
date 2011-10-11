package connection.widget;

import com.example.prototype.api.widget.WidgetFactory;
import connection.api.ConnectionListener;
import connection.api.ConnectionMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

class ConnectionWidget extends JComponent implements ConnectionListener {

    public ConnectionWidget(ConnectionMonitor connectionMonitor) {
        setPreferredSize(new Dimension(20, 80));
        connectionMonitor.addListener(this);

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
        int y0 = height * okPercentage / 100;
        int y1 = height - y0;
        graphics.fillRect(0, y1, width, y0);
        graphics.setColor(Color.BLUE);
        graphics.drawRect(0, y1, width, y0);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width, height);
    }

    @Override
    public void onStateChange(int okPercentage)
    {
        this.okPercentage = okPercentage;
        repaint();
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
    final static String NAME = "connection.status";
}
