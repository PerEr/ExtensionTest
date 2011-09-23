package test;

import api.Command;
import api.EventListener;
import api.EventSource;
import api.Plugin;

public class TestPlugin implements Plugin, EventListener {

    private Command m_unsubscribeCommand;

    public void load(EventSource eventSource) {
        m_unsubscribeCommand = eventSource.addListener(this);
    }

    public void unload() {
        m_unsubscribeCommand.process();
    }

    public void onEvent() {
        // TODO
    }
}
