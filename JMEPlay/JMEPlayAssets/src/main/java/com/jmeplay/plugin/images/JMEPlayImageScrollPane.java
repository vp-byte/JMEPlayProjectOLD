package com.jmeplay.plugin.images;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

/**
 * Create AssetsTreeCell to manage actions on tree cells
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@SuppressWarnings("FieldCanBeLocal")
class JMEPlayImageScrollPane extends ScrollPane {
    private final double SCROLL_DELTA = 1.1;
    private ObjectProperty<Point2D> mouseCoordinates = new SimpleObjectProperty<>();
    private ObjectProperty<Point2D> lastMousePressedCoordinates = new SimpleObjectProperty<>();

    private DoubleProperty zoomPropertyWidth;
    private DoubleProperty zoomPropertyHight;
    private JMEPlayImageView imageView;
    private BorderPane borderPane;
    private VBox vBox;

    public JMEPlayImageScrollPane(Path path) {
        Image image = new Image("file:" + path.toAbsolutePath().toString());
        imageView = new JMEPlayImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);

        zoomPropertyWidth = new SimpleDoubleProperty(imageView.minWidth(image.getWidth()));
        zoomPropertyHight = new SimpleDoubleProperty(imageView.minHeight(image.getHeight()));


        borderPane = new BorderPane();
        borderPane.setCenter(imageView);



        vBox = new VBox(borderPane);
        vBox.setAlignment(Pos.CENTER);
        vBox.minWidthProperty().bind(widthProperty());
        vBox.minHeightProperty().bind(heightProperty());

        imageView.setOnMouseMoved(this::processMouseMoved);

        borderPane.prefWidthProperty().bind(zoomPropertyWidth);
        borderPane.prefHeightProperty().bind(zoomPropertyHight);
        borderPane.setOnMousePressed(this::processMousePressed);
        borderPane.setOnMouseDragged(this::processMouseDragged);

        addEventFilter(ScrollEvent.ANY, this::processScroll);
        setContent(new Group(vBox));
    }

    private void processMouseMoved(MouseEvent event) {
        mouseCoordinates.set(new Point2D(event.getX(), event.getY()));
    }

    /**
     * Handle scroll event to scale view
     *
     * @param event to scroll
     */
    private void processScroll(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            zoomPropertyWidth.set(zoomPropertyWidth.get() * SCROLL_DELTA);
            zoomPropertyHight.set(zoomPropertyHight.get() * SCROLL_DELTA);
        } else if (event.getDeltaY() < 0) {
            if (zoomPropertyWidth.get() > imageView.minWidth(imageView.getImage().getHeight())
                    && zoomPropertyHight.get() > imageView.minHeight(imageView.getImage().getWidth())) {
                zoomPropertyWidth.set(zoomPropertyWidth.get() / SCROLL_DELTA);
                zoomPropertyHight.set(zoomPropertyHight.get() / SCROLL_DELTA);
            }
        }

        layout();
        setHvalue(0.5);
        setVvalue(0.5);

    }

    /**
     * Handle mouse pressed event to set last mouse coordinates
     *
     * @param event of mouse pressed
     */
    private void processMousePressed(MouseEvent event) {
        lastMousePressedCoordinates.set(new Point2D(event.getX(), event.getY()));
    }

    /**
     * Handle mouse dragged event to set scroll position of scroll pane
     *
     * @param event of mouse dragged
     */
    private void processMouseDragged(MouseEvent event) {
        double deltaX = event.getX() - lastMousePressedCoordinates.get().getX();
        double extraWidth = borderPane.getLayoutBounds().getWidth() - getViewportBounds().getWidth();
        double deltaH = deltaX * (getHmax() - getHmin()) / extraWidth;
        double desiredH = getHvalue() - deltaH;
        setHvalue(Math.max(0, Math.min(getHmax(), desiredH)));

        double deltaY = event.getY() - lastMousePressedCoordinates.get().getY();
        double extraHeight = borderPane.getLayoutBounds().getHeight() - getViewportBounds().getHeight();
        double deltaV = deltaY * (getHmax() - getHmin()) / extraHeight;
        double desiredV = getVvalue() - deltaV;
        setVvalue(Math.max(0, Math.min(getVmax(), desiredV)));
    }

}
