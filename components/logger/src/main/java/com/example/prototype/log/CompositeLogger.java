package com.example.prototype.log;

import com.example.prototype.api.services.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class CompositeLogger implements Logger {
    @Override
    public void logError(String message) {
        for (Logger logger : loggers) {
            logger.logError(message);
        }
    }

    @Override
    public void logInfo(String message) {
        for (Logger logger : loggers) {
            logger.logInfo(message);
        }
    }

    @Override
    public void logDebug(String message) {
        for (Logger logger : loggers) {
            logger.logDebug(message);
        }
    }

    void addLogger(Logger logger) {
        loggers.add(logger);
    }

    public void clearAll() {
        loggers.clear();
    }

    private final List<Logger> loggers = new LinkedList<Logger>();

}
