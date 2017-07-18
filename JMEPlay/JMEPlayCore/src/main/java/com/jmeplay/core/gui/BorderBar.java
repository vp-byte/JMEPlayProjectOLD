package com.jmeplay.core.gui;

import com.jmeplay.core.Position;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Border bar for right, left or bottom position
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class BorderBar extends VBox {

    private final Position position;

    public BorderBar(double expandedSize, Position position, Node... nodes) {
        this.position = position;
        getChildren().addAll(nodes);
        defineSize(expandedSize);
    }

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