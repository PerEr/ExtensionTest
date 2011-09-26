package api.services;

public interface Logger {

    void logError(String message);
    void logInfo(String message);
    void logDebug(String message);

}
