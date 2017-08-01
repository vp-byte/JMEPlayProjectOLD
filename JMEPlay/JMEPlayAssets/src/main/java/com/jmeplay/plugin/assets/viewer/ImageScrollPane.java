package com.jmeplay.plugin.assets.viewer;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;

import java.lang.reflect.Field;
import java.nio.file.Path;

public class ImageScrollPane extends ScrollPane {
    private DoubleProperty zoomPropertyWidth;
    private DoubleProperty zoomPropertyHight;
    private ImageView imageView = new ImageView();

    public ImageScrollPane(Path path) {
        Image image = new Image("file:" + path.toAbsolutePath().toString());
        zoomPropertyWidth = new SimpleDoubleProperty(image.getWidth());
        zoomPropertyHight = new SimpleDoubleProperty(image.getHeight());
        imageView.setImage(image);

        zoomPropertyWidth.addListener((Observable listener) -> {
            zoom();
            if (!isHScrollBarVisible()) {
                imageView.setTranslateX(widthProperty().doubleValue() / 2 - imageView.fitWidthProperty().doubleValue() / 2);
            } else {
                imageView.setTranslateX(0);
                setHvalue(0.5);
            }
        });
        zoomPropertyHight.addListener((Observable listener) -> {
            zoom();
            if (!isVScrollBarVisible()) {
                imageView.setTranslateY(heightProperty().doubleValue() / 2 - imageView.fitHeightProperty().doubleValue() / 2);
            } else {
                imageView.setTranslateY(0);
                setVvalue(0.5);
            }
        });

        addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() > 0) {
                zoomPropertyWidth.set(zoomPropertyWidth.get() * 1.1);
                zoomPropertyHight.set(zoomPropertyHight.get() * 1.1);
            } else if (event.getDeltaY() < 0) {
                zoomPropertyWidth.set(zoomPropertyWidth.get() / 1.1);
                zoomPropertyHight.set(zoomPropertyHight.get() / 1.1);
            }
        });

        setContent(imageView);
    }

    private boolean isHScrollBarVisible() {
        return isScrollBarVisible("hsb");
    }

    private boolean isVScrollBarVisible() {
        return isScrollBarVisible("vsb");
    }

    private boolean isScrollBarVisible(String fieldName) {
        try {
            ScrollPaneSkin skin = (ScrollPaneSkin) getSkin();
            Field field = skin.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            ScrollBar scrollBar = (ScrollBar) field.get(skin);
            return scrollBar.isVisible();
        } catch (Exception ex) {
            return false;
        }
    }

    public void zoom() {
        imageView.setFitWidth(zoomPropertyWidth.get());
        imageView.setFitHeight(zoomPropertyHight.get());
    }
}
