package com.jmeplay.core;

/**
 * Interface to handle console output
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public interface JMEPlayConsole {

    /**
     * Write message to console
     *
     * @param messageType {@link MessageType}
     * @param message     simple string message
     */
    void writeMessage(MessageType messageType, String message);

    /**
     * Write exception directly to console
     *
     * @param exception all types
     */
    void writeException(Exception exception);

    /**
     * Type of message
     */
    enum MessageType {
        ERROR,
        WARN,
        INFO,
        SUCCESS
    }
}
