package app.base;

import api.Command;
import api.EventListener;
import api.EventSource;

import java.util.LinkedList;
import java.util.List;

public class EventManager implements EventSource {
    public Command addListener(final EventListener listener) {
        m_listeners.add(listener);
        return new Command() {
            public void process() {
                removeListener(listener);
            }
        };
    }

    private void removeListener(EventListener listener) {
        m_listeners.remove(listener);
    }

    private List<EventListener> m_listeners = new LinkedList<EventListener>();
}
