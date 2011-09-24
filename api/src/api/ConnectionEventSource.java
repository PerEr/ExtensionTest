package api;

public interface ConnectionEventSource {
    Command addListener(ConnectionEventListener listener);
}
