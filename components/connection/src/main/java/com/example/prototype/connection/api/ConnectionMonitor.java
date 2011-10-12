package com.example.prototype.connection.api;

public interface ConnectionMonitor {

    void addListener(ConnectionListener listener);
    void removeListener(ConnectionListener listener);
}
