package test;

import api.*;

public class TestPlugin implements Plugin, EventListener {

    private Command m_unsubscribeCommand;

    public void load(ServiceLookup lookup) {
        EventSource src = (EventSource) lookup.lookupService(EventSource.class);
        m_unsubscribeCommand = src.addListener(this);
    }

    public void unload() {
        m_unsubscribeCommand.process();
    }

    public void onEvent() {
        // TODO
    }
}
