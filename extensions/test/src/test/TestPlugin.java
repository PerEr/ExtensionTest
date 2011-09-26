package test;

import api.*;

public class TestPlugin implements Plugin {

    //private Command m_unsubscribeCommand;

    public void load(ServiceLookup lookup) {
        //ConnectionEventSource src = (ConnectionEventSource) lookup.lookupService(ConnectionEventSource.class);
        //m_unsubscribeCommand = src.addListener(this);
    }

    public void unload() {
        // m_unsubscribeCommand.process();
    }

}
