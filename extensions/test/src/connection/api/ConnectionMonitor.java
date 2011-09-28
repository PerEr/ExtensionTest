package connection.api;

public interface ConnectionMonitor {

    void addListener(ConnectionListener listener);
    void removeListener(ConnectionListener listener);
}
