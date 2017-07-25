package com.jmeplay.core.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoader {

    public static ImageView initImageView(String imagePath) {
        return initImageView(imagePath, null, null);
    }

    public static ImageView initImageView(String imagePath, Integer width, Integer height) {
        Image image = new Image(ImageLoader.class.getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        if (width != null) imageView.setFitWidth(width);
        if (height != null) imageView.setFitHeight(height);
        return imageView;
    }

}
