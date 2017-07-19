package com.jmeplay.core;

/**
 * Interface to handle info messages
 */
public interface BottomInfoMessage {
    /**
     * Set info message to the bottom view of the editor
     *
     * @param message as string text
     */
    void writeInfoMessage(String message);

    /**
     * @return actual info message
     */
    String infoMessage();
}
