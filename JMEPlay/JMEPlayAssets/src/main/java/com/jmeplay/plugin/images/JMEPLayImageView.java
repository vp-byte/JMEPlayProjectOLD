package com.jmeplay.plugin.images;

import javafx.scene.image.ImageView;

/**
 * Image view to fill and fit parent with ImageView
 */
public class JMEPLayImageView extends ImageView {
    private double minHeight = 1024;
    private double minWidth = 1024;

    @Override
    public double minHeight(double width) {
        double imageHeight = getImage().getHeight();
        return imageHeight < minHeight ? imageHeight : minHeight;
    }

    @Override
    public double maxHeight(double width) {
        return Double.MAX_VALUE;
    }

    @Override
    public double prefHeight(double width) {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height) {
        double imageWidth = getImage().getWidth();
        return imageWidth < minWidth ? imageWidth : minWidth;
    }

    @Override
    public double maxWidth(double height) {
        return Double.MAX_VALUE;
    }

    @Override
    public double prefWidth(double height) {
        return minWidth(height);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        super.setFitWidth(width);
        super.setFitHeight(height);
    }
}
