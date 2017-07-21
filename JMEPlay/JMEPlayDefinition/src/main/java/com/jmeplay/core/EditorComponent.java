package com.jmeplay.core;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Abstract class to define new component in Editor on left, right, bottom side
 */
public abstract class EditorComponent {
    private int priority = Integer.MAX_VALUE;
    private Position position = Position.LEFT;

    /**
     * Unique id to identify implemented editor component
     * Do not use dynamic id
     *
     * @return unique UUID
     */
    public abstract String uniqueId();

    /**
     * @return priority to place label of component in view
     */
    int getPriority() {
        return priority;
    }

    /**
     * Set priority to manage label position in view
     *
     * @param priority of component
     */
    void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return name of editor component
     */
    public abstract String name();

    /**
     * @return description of editor component
     */
    public abstract String description();

    /**
     * @return position of component left, right or bottom
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Change position of component
     *
     * @param position in view
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Label to add in border bar
     *
     * @return designed label
     */
    public abstract Label label();

    /**
     * Main view of component as node
     *
     * @return view as node
     */
    public abstract Node component();

}
