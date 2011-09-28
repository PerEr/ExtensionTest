package connection.monitor;

import connection.api.ConnectionListener;
import connection.api.ConnectionMonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class ConnectionMonitorImpl implements ConnectionMonitor {

    ConnectionMonitorImpl() {
        okPercentage = 50;

        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onTimer();
            }
        });
        timer.start();
    }

    private void onTimer() {
        // Simulate random drift in the estimate
        int next = random.nextInt() % 10;
        next += okPercentage;
        next = Math.min(100, next);
        next = Math.max(0, next);

        okPercentage = next;

        for (ConnectionListener listener : listeners) {
            listener.onStateChange(okPercentage);
        }
    }

    @Override
    public void addListener(ConnectionListener listener) {
        assert listener != null;
        listeners.add(listener);
    }

    @Override
    public void removeListener(ConnectionListener listener) {
        assert listener != null;
        boolean ok = listeners.remove(listener);
        assert ok;
    }

    // 0..100% connection
    Random random = new Random(System.currentTimeMillis());
    int okPercentage;

    private final LinkedList<ConnectionListener> listeners = new LinkedList<ConnectionListener>();
}
