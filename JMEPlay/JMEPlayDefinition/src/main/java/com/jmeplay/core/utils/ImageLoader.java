package com.jmeplay.core.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoader {

	public static ImageView initImageView(String imagePath, double width, double height) {
		Image image = new Image(ImageLoader.class.getClass().getResourceAsStream(imagePath));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		return imageView;
	}

}
