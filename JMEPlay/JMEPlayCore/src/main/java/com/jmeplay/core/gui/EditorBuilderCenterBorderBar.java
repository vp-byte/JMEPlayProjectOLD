package com.jmeplay.core.gui;

import com.jmeplay.core.utils.Settings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create border bars for the left, right and bottom view of center in editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderCenterBorderBar {

    private VBox left;
    private List<Node> borderItemsLeft;
    private BorderBar borderBarLeft;
    private Object selectedLeft;

    private VBox right;
    private List<Node> borderItemsRight;
    private BorderBar borderBarRight;
    private Object selectedRight;

    private HBox bottom;
    private List<Node> borderItemsBottom;
    private BorderBar borderBarBottom;
    private Object selectedBottom;

    @Autowired
    private Settings settings;

    @Autowired
    private EditorBuilderCenterCenter editorBuilderCenterCenter;

    @Autowired(required = false)
    private List<EditorComponent> editorComponents;

    /**
     * Initialize center view with all border bars depends on spring context
     */
    @PostConstruct
    private void init() {
        initElements();
        initLeft();
        initRight();
        initBottom();
    }

    /**
     * Initialize all view elements from spring context
     */
    private void initElements() {
        borderItemsLeft = new ArrayList<>();
        borderItemsRight = new ArrayList<>();
        borderItemsBottom = new ArrayList<>();
        if (editorComponents == null) {
            return;
        }
        for (Position position : Position.values()) {
            boolean first = true;
            List<EditorComponent> components = editorComponents.stream()
                    .filter(component -> component.getPosition() == position).collect(Collectors.toList());
            for (EditorComponent component : components) {
                Node borderItem = null;
                if (first) {
                    borderItem = initBorderItem(component, true);
                    first = false;
                } else {
                    borderItem = initBorderItem(component, false);
                }
                if (position == Position.LEFT) {
                    borderItemsLeft.add(borderItem);
                } else if (position == Position.RIGHT) {
                    borderItemsRight.add(borderItem);
                } else if (position == Position.BOTTOM) {
                    borderItemsBottom.add(borderItem);
                }
            }
        }
    }

    /**
     * Initialize border item depends on {@link EditorComponent}
     *
     * @param component component extends from {@link EditorComponent}
     * @param selected  show the component and mark as selected
     * @return node of border item
     */
    private Node initBorderItem(EditorComponent component, boolean selected) {
        Label label = component.label();
        Position position = component.getPosition();
        label.setMinHeight(settings.getOptionInteger("guiIconSize") - 2);
        label.getStyleClass().remove("borderbar-label");
        label.getStyleClass().add("borderbar-label");
        if (position == Position.LEFT) {
            label.setRotate(-90);
        }
        if (position == Position.RIGHT) {
            label.setRotate(90);
        }
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleBorderItem(event.getSource(), position));
        if (selected) {
            handleBorderItem(label, position);
        }
        return new Group(label);
    }

    /**
     * Initialize left border bar view
     */
    private void initLeft() {
        left = new VBox();
        left.getChildren().addAll(borderItemsLeft);
        borderBarLeft = new BorderBar(settings.getOptionInteger("guiIconSize"), Position.LEFT, left);
        left.prefHeightProperty().bind(borderBarLeft.heightProperty());
    }

    /**
     * Initialize right border bar view
     */
    private void initRight() {
        right = new VBox();
        right.getChildren().addAll(borderItemsRight);
        borderBarRight = new BorderBar(settings.getOptionInteger("guiIconSize"), Position.RIGHT, right);
        right.prefHeightProperty().bind(borderBarRight.heightProperty());
    }

    /**
     * Initialize bottom border bar view
     */
    private void initBottom() {
        bottom = new HBox();
        bottom.getStyleClass().add("borderbar");
        Label labelMargin = new Label();
        labelMargin.setMinWidth(settings.getOptionInteger("guiIconSize"));
        bottom.getChildren().add(labelMargin);

        bottom.getChildren().addAll(borderItemsBottom);
        borderBarBottom = new BorderBar(settings.getOptionInteger("guiIconSize"), Position.BOTTOM, bottom);
        bottom.prefHeightProperty().bind(borderBarBottom.heightProperty());
    }

    /**
     * Handle action from border bar item
     *
     * @param source   of event
     * @param position of the border bar and item
     */
    private void handleBorderItem(Object source, Position position) {
        List<Node> borderItems = null;
        if (position == Position.LEFT) {
            borderItems = borderItemsLeft;
        } else if (position == Position.RIGHT) {
            borderItems = borderItemsRight;
        } else {
            borderItems = borderItemsBottom;
        }
        borderItems.forEach((control) -> {
            ((Group) control).getChildren().get(0).getStyleClass().remove("borderbar-label-selected");
            ((Group) control).getChildren().get(0).getStyleClass().add("borderbar-label");
        });

        if (position == Position.LEFT) {
            handleBorderItemLeft(source);
        } else if (position == Position.RIGHT) {
            handleBorderItemRight(source);
        } else {
            handleBorderItemBottom(source);
        }
    }

    /**
     * Handle action from left border bar item
     *
     * @param source of event
     */
    private void handleBorderItemLeft(Object source) {
        if (source == selectedLeft && !editorBuilderCenterCenter.isLeftRemoved()) {
            editorBuilderCenterCenter.removeLeft();
        } else {
            List<EditorComponent> comp = editorComponents.stream().filter(component -> component.label() == source)
                    .collect(Collectors.toList());
            if (comp.size() > 0) {
                editorBuilderCenterCenter.setLeft(comp.get(0).component());
            }
            ((Node) source).getStyleClass().remove("borderbar-label");
            ((Node) source).getStyleClass().add("borderbar-label-selected");
        }
        selectedLeft = source;
    }

    /**
     * Handle action from right border bar item
     *
     * @param source of event
     */
    private void handleBorderItemRight(Object source) {
        if (source == selectedRight && !editorBuilderCenterCenter.isRightRemoved()) {
            editorBuilderCenterCenter.removeRight();
        } else {
            List<EditorComponent> comp = editorComponents.stream().filter(component -> component.label() == source)
                    .collect(Collectors.toList());
            if (comp.size() > 0) {
                editorBuilderCenterCenter.setRight(comp.get(0).component());
            }
            ((Node) source).getStyleClass().remove("borderbar-label");
            ((Node) source).getStyleClass().add("borderbar-label-selected");
        }
        selectedRight = source;
    }

    /**
     * Handle action from bottom border bar item
     *
     * @param source of event
     */
    private void handleBorderItemBottom(Object source) {
        if (source == selectedBottom && !editorBuilderCenterCenter.isBottomRemoved()) {
            editorBuilderCenterCenter.removeBottom();
        } else {
            List<EditorComponent> comp = editorComponents.stream().filter(component -> component.label() == source)
                    .collect(Collectors.toList());
            if (comp.size() > 0) {
                editorBuilderCenterCenter.setBottom(comp.get(0).component());
            }
            ((Node) source).getStyleClass().remove("borderbar-label");
            ((Node) source).getStyleClass().add("borderbar-label-selected");
        }
        selectedBottom = source;
    }

    /**
     * @return left border bar
     */
    public Node borderBarLeft() {
        return borderBarLeft;
    }

    /**
     * @return right border bar
     */
    public Node borderBarRight() {
        return borderBarRight;
    }

    /**
     * @return bottom border bar
     */
    public Node borderBarBottom() {
        return borderBarBottom;
    }
}
