package com.jmeplay.plugin.assets.viewer;

import com.jmeplay.core.gui.EditorViewer;
import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class to define new component in Editor on left, right, bottom side
 */
@Component
public class ImageViewer extends EditorViewer {


    @PostConstruct
    private void init() {

    }

    @Override
    public List<String> filetypes() {
        return Arrays.asList("jpg", "jpeg", "png", "svg");
    }

    @Override
    public EditorViewerTab view(final Path path) {

        ImageView imageView = new ImageView();
        ScrollPane scrollPane = new ScrollPane();
        final DoubleProperty zoomProperty = new SimpleDoubleProperty(100);

        zoomProperty.addListener((Observable listener) -> {
            zoom(imageView, zoomProperty);

            try {
                final ScrollPaneSkin skin = (ScrollPaneSkin) scrollPane.getSkin();
                final Field field = skin.getClass().getDeclaredField("hsb");
                field.setAccessible(true);
                final ScrollBar scrollBar = (ScrollBar) field.get(skin);
                field.setAccessible(false);

                if (!scrollBar.isVisible()) {
                    imageView.setTranslateX(scrollPane.widthProperty().doubleValue() / 2 - imageView.fitWidthProperty().doubleValue() / 3);
                } else {
                    System.out.println("imageView " + imageView.fitWidthProperty().doubleValue());
                    System.out.println("scrollPane " + scrollPane.widthProperty().doubleValue());
                    imageView.setTranslateX(0);
                    scrollPane.setHvalue(0.5);
                }
            }catch (Exception e) {

            }

            /*
            if (scrollPane.heightProperty().doubleValue() > imageView.fitHeightProperty().doubleValue()) {
                imageView.setTranslateY(scrollPane.heightProperty().doubleValue() / 2 - imageView.fitHeightProperty().doubleValue() / 3);
            } else {
                imageView.setTranslateY(0);
                scrollPane.setVvalue(0.5);
            }*/
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }


            }
        });

        imageView.setImage(new Image("file:" + path.toAbsolutePath().toString()));
        imageView.preserveRatioProperty().set(true);
        zoom(imageView, zoomProperty);

        scrollPane.setContent(imageView);

        EditorViewerTab tab = super.view(path);
        tab.setContent(scrollPane);
        return tab;
    }

    private void zoom(ImageView imageView, final DoubleProperty zoomProperty) {
        imageView.setFitWidth(zoomProperty.get() * 2);
        imageView.setFitHeight(zoomProperty.get() * 2);
    }

}
