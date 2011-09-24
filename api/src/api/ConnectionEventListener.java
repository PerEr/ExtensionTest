package api;

public interface ConnectionEventListener {
    void onConnectionEstablished();
    void onConnectionLost();
    void onDisconnect();
}
