package com.jmeplay.core;

/**
 * Interface to handle console output
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public interface JMEPlayConsole {

    void writeMessage(MessageType messageType, String message);

    void writeException(Exception exception);

    enum MessageType {
        ERROR,
        WARN,
        INFO,
        SUCCESS
    }
}
