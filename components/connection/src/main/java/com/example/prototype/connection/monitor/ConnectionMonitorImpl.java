package com.example.prototype.connection.monitor;

import com.example.prototype.connection.api.ConnectionListener;
import com.example.prototype.connection.api.ConnectionMonitor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

class ConnectionMonitorImpl implements ConnectionMonitor {

    ConnectionMonitorImpl() {
        okPercentage = 50;
        timer.start();
    }

    private void onTimer() {
        // Simulate random drift in the estimate
        int next = random.nextInt() % 5;
        next += okPercentage;
        next = Math.min(100, next);
        next = Math.max(0, next);

        okPercentage = next;

        Iterator<WeakReference<ConnectionListener>> ii = listeners.iterator();
        while (ii.hasNext()) {
            ConnectionListener listener = ii.next().get();
            if (listener == null) {
                ii.remove();
            } else {
                listener.onStateChange(okPercentage);
            }
        }
    }

    @Override
    public void addListener(ConnectionListener listener) {
        assert listener != null;
        listeners.add(new WeakReference<ConnectionListener>(listener));
    }

    @Override
    public void removeListener(ConnectionListener listener) {
        boolean ok = false;
        assert listener != null;
        Iterator<WeakReference<ConnectionListener>> ii = listeners.iterator();
        while (ii.hasNext()) {
            ConnectionListener ll = ii.next().get();
            if (ll == listener) {
                ii.remove();
                ok = true;
            }
        }
        assert ok;
    }

    void dispose() {
        timer.stop();
        listeners.clear();
    }

    private Timer timer = new Timer(250, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            onTimer();
        }
    });

    // 0..100% connection
    Random random = new Random(System.currentTimeMillis());
    int okPercentage;

    private final LinkedList<WeakReference<ConnectionListener>> listeners =
        new LinkedList<WeakReference<ConnectionListener>>();
}
