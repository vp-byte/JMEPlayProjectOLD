package com.jmeplay.plugin.images;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

import java.nio.file.Path;

/**
 * Create AssetsTreeCell to manage actions on tree cells
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@SuppressWarnings("FieldCanBeLocal")
class JMEPLayImageScrollPane extends ScrollPane {
    private final double SCALE_DELTA = 1.1;

    private Path path;
    private ImageView imageView;
    private Group group;
    private StackPane zoomPane;
    private Group scrollContent;

    private ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();

    JMEPLayImageScrollPane(Path path) {
        this.path = path;
        initImageView();
        initScrollContent();
        setContent(scrollContent);
    }

    /**
     * Initialize image view with image from path
     */
    private void initImageView() {
        Image image = new Image("file:" + path.toAbsolutePath().toString());
        imageView = new ImageView();
        imageView.setImage(image);
    }

    /**
     * Initialize scroll content
     */
    private void initScrollContent() {
        group = new Group(imageView);
        zoomPane = new StackPane();
        zoomPane.getChildren().add(group);
        scrollContent = new Group(zoomPane);

        viewportBoundsProperty().addListener((observable, oldValue, newValue) -> zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight()));

        zoomPane.setOnScroll(this::processScroll);
        scrollContent.setOnMousePressed(this::processMousePressed);
        scrollContent.setOnMouseDragged(this::processMouseDragged);
    }

    /**
     * Handle scroll event to scale view
     *
     * @param event to scroll
     */
    private void processScroll(ScrollEvent event) {
        event.consume();

        if (event.getDeltaY() == 0) {
            return;
        }

        Point2D scrollOffset = figureScrollOffset();

        double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
        group.setScaleX(group.getScaleX() * scaleFactor);
        group.setScaleY(group.getScaleY() * scaleFactor);
        repositionScroller(scaleFactor, scrollOffset);
    }

    /**
     * Handle mouse pressed event to set last mouse coordinates
     *
     * @param event of mouse pressed
     */
    private void processMousePressed(MouseEvent event) {
        lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
    }

    /**
     * Handle mouse dragged event to set scroll position of scroll pane
     *
     * @param event of mouse dragged
     */
    private void processMouseDragged(MouseEvent event) {
        double deltaX = event.getX() - lastMouseCoordinates.get().getX();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - getViewportBounds().getWidth();
        double deltaH = deltaX * (getHmax() - getHmin()) / extraWidth;
        double desiredH = getHvalue() - deltaH;
        setHvalue(Math.max(0, Math.min(getHmax(), desiredH)));

        double deltaY = event.getY() - lastMouseCoordinates.get().getY();
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - getViewportBounds().getHeight();
        double deltaV = deltaY * (getHmax() - getHmin()) / extraHeight;
        double desiredV = getVvalue() - deltaV;
        setVvalue(Math.max(0, Math.min(getVmax(), desiredV)));
    }

    /**
     * Amount of scrolling in each direction in scrollContent coordinate units
     *
     * @return scroll offset x and y as {@link Point2D}
     */
    private Point2D figureScrollOffset() {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - getViewportBounds().getWidth();
        double hScrollProportion = (getHvalue() - getHmin()) / (getHmax() - getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - getViewportBounds().getHeight();
        double vScrollProportion = (getVvalue() - getVmin()) / (getVmax() - getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    /**
     * Move viewport so that old center remains in the center after thescaling
     *
     * @param scaleFactor  factor of scale
     * @param scrollOffset scroll offset x and y as {@link Point2D}
     */
    private void repositionScroller(double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = getViewportBounds().getWidth() / 2;
            double newScrollXOffset = (scaleFactor - 1) * halfWidth + scaleFactor * scrollXOffset;
            setHvalue(getHmin() + newScrollXOffset * (getHmax() - getHmin()) / extraWidth);
        } else {
            setHvalue(getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = getViewportBounds().getHeight() / 2;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            setVvalue(getVmin() + newScrollYOffset * (getVmax() - getVmin()) / extraHeight);
        } else {
            setHvalue(getHmin());
        }
    }
}
