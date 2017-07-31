package com.jmeplay.core.gui;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Border bar for right, left or bottom position
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class BorderBar extends VBox {

    private final Position position;

    /**
     * Create border bar
     *
     * @param expandedSize of border bar
     * @param position     of border bar {@link Position}
     * @param nodes        to show in the border bar
     */
    public BorderBar(double expandedSize, Position position, Node... nodes) {
        this.position = position;
        getChildren().addAll(nodes);
        defineSize(expandedSize);
    }

    /**
     * Set prefered hight or width depends on position
     *
     * @param size of border bar
     */
    private void defineSize(double size) {
        switch (position) {
            case BOTTOM:
                setPrefHeight(size);
                break;
            case LEFT:
            case RIGHT:
                setPrefWidth(size);
                break;
            default:
                return;
        }
    }

}