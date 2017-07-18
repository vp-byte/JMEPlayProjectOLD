package com.jmeplay.core;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Abstract class to define new component in Editor on left, right, bottom side
 */
public abstract class EditorComponent {
    private int priority = Integer.MAX_VALUE;
    private Position position = Position.LEFT;

    public abstract String uniqueId();

    int getPriority() {
        return priority;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract String name();

    public abstract String description();

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract Label label();

    public abstract Node component();

}
