package log;

import api.services.Logger;

public class BasicLogger implements Logger {
    public void logError(String message) {
        System.err.println("Err:" + message);
    }

    public void logInfo(String message) {
        System.out.println("Info:" + message);
    }

    public void logDebug(String message) {
        System.out.println("Dbg:" + message);
    }
}
