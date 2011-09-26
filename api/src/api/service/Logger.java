package api.service;

public interface Logger {

    void logError(String message);
    void logInfo(String message);
    void logDebug(String message);

}
