package app.base;

import api.Command;
import api.ConnectionEventListener;
import api.ConnectionEventSource;

import java.util.LinkedList;
import java.util.List;

public class ConnectionEventManager implements ConnectionEventSource, ConnectionEventListener {

    public Command addListener(final ConnectionEventListener listener) {
        m_listeners.add(listener);
        return new Command() {
            public void process() {
                removeListener(listener);
            }
        };
    }

    private void removeListener(ConnectionEventListener listener) {
        m_listeners.remove(listener);
    }

    private List<ConnectionEventListener> m_listeners = new LinkedList<ConnectionEventListener>();


    // For test purposes
    public void onConnectionEstablished() {
        for (ConnectionEventListener listener : m_listeners) {
            listener.onConnectionEstablished();
        }
    }

    public void onConnectionLost() {
        for (ConnectionEventListener listener : m_listeners) {
            listener.onConnectionLost();
        }
    }

    public void onDisconnect() {
        for (ConnectionEventListener listener : m_listeners) {
            listener.onDisconnect();
        }
    }
}
