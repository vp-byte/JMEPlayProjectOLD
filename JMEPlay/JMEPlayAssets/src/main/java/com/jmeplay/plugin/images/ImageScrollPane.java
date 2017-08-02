package com.jmeplay.plugin.images;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import java.lang.reflect.Field;
import java.nio.file.Path;

public class ImageScrollPane extends ScrollPane {
    private DoubleProperty zoomPropertyWidth;
    private DoubleProperty zoomPropertyHeight;
    private ImageView imageView;

    public ImageScrollPane(Path path) {
        Image image = new Image("file:" + path.toAbsolutePath().toString());
        zoomPropertyWidth = new SimpleDoubleProperty(image.getWidth());
        zoomPropertyHeight = new SimpleDoubleProperty(image.getHeight());
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);

        zoomPropertyWidth.addListener((Observable listener) -> {
            zoom();
            centerHorizontal();
        });
        zoomPropertyHeight.addListener((Observable listener) -> {
            zoom();
            centerVertical();
        });

        addEventFilter(ScrollEvent.ANY, scrollEvent -> {
            if (scrollEvent.getDeltaY() > 0) {
                zoomPropertyWidth.set(zoomPropertyWidth.get() * 1.1);
                zoomPropertyHeight.set(zoomPropertyHeight.get() * 1.1);
            } else if (scrollEvent.getDeltaY() < 0) {
                zoomPropertyWidth.set(zoomPropertyWidth.get() / 1.1);
                zoomPropertyHeight.set(zoomPropertyHeight.get() / 1.1);
            }
        });

        setOnMouseMoved(mouseEvent -> {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
                System.out.println(mouseEvent.getX() + "," + mouseEvent.getY());
            }
        });

        setContent(imageView);
    }

    private void centerVertical() {
        if (!isVScrollBarVisible()) {
            imageView.setTranslateY(heightProperty().doubleValue() / 2 - imageView.fitHeightProperty().doubleValue() / 2);
        } else {
            imageView.setTranslateY(0);
            setVvalue(0.5);
        }
    }

    private void centerHorizontal() {
        if (!isHScrollBarVisible()) {
            imageView.setTranslateX(widthProperty().doubleValue() / 2 - imageView.fitWidthProperty().doubleValue() / 2);
        } else {
            imageView.setTranslateX(0);
            setHvalue(0.5);
        }
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

    private void zoom() {
        imageView.setFitWidth(zoomPropertyWidth.get());
        imageView.setFitHeight(zoomPropertyHeight.get());
    }

    public void center() {
        centerVertical();
        centerHorizontal();
    }
}
