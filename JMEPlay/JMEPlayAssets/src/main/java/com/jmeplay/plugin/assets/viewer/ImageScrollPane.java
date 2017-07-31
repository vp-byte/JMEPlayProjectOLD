package com.jmeplay.plugin.assets.viewer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public class ImageScrollPane {
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(100);
    private ImageView imageView = new ImageView();
}
