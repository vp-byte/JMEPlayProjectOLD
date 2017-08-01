package com.jmeplay.core.gui;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create center view to render
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderCenterCenter implements EditorCenter {

    private TabPane centerTabPane;
    private Node centerNode;

    private Node leftNode;
    private boolean leftRemoved = false;
    private Node rightNode;
    private boolean rightRemoved = false;
    private Node bottomNode;
    private boolean bottomRemoved = false;

    private SplitPane topSplitPane;
    private SplitPane splitPane;
    private double dividerPositions[] = {0.2, 0.8, 0.8};

    /**
     * Initialize center view
     */
    @PostConstruct
    private void init() {
        centerTabPane = new TabPane();
        centerNode = new StackPane(centerTabPane);
        leftNode = new StackPane(new Text("Left"));
        rightNode = new StackPane(new Text("Right"));
        bottomNode = new StackPane(new Text("Bottom"));

        initTopSplitPane();
        initSplitPane();
    }

    @Override
    public TabPane centerView() {
        return centerTabPane;
    }

    /**
     * Initialize view of the top split pane
     */
    private void initTopSplitPane() {
        topSplitPane = new SplitPane();
        topSplitPane.setOrientation(Orientation.HORIZONTAL);
        topSplitPane.getItems().addAll(leftNode, centerNode, rightNode);
    }

    /**
     * Initialize view of the split pane
     */
    private void initSplitPane() {
        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(topSplitPane, bottomNode);
    }

    /**
     * // FIXME set the position of divider with always save for SplitPanes
     * Reset positions of dividers
     */
    public void resetDividerPositions() {
        splitPane.setDividerPositions(dividerPositions[2]);
        if (isLeftRemoved() && isRightRemoved()) {
            return;
        }
        if (isLeftRemoved()) {
            topSplitPane.setDividerPositions(dividerPositions[1]);
            return;
        }
        if (isRightRemoved()) {
            topSplitPane.setDividerPositions(dividerPositions[0]);
            return;
        }

        topSplitPane.setDividerPositions(dividerPositions[0], dividerPositions[1]);
    }

    /**
     * Create center view for editor
     *
     * @return
     */
    public SplitPane view() {
        return splitPane;
    }

    /**
     * remove left view
     */
    public void removeLeft() {
        topSplitPane.getItems().remove(leftNode);
        leftRemoved = true;
    }

    /**
     * @return is left view removed
     */
    public boolean isLeftRemoved() {
        return leftRemoved;
    }

    /**
     * Set left view
     *
     * @param node to view
     */
    public void setLeft(Node node) {
        if (isLeftRemoved()) {
            topSplitPane.getItems().add(0, node);
        } else {
            topSplitPane.getItems().set(0, node);
        }
        leftNode = node;
        leftRemoved = false;
    }

    /**
     * remove right view
     */
    public void removeRight() {
        topSplitPane.getItems().remove(rightNode);
        rightRemoved = true;
    }

    /**
     * @return is right view removed
     */
    public boolean isRightRemoved() {
        return rightRemoved;
    }

    /**
     * Set right view
     *
     * @param node to view
     */
    public void setRight(Node node) {
        int index = isLeftRemoved() ? 1 : 2;
        if (isRightRemoved()) {
            topSplitPane.getItems().add(index, node);
        } else {
            topSplitPane.getItems().set(index, node);
        }
        rightNode = node;
        rightRemoved = false;
    }

    /**
     * remove bottom view
     */
    public void removeBottom() {
        splitPane.getItems().remove(bottomNode);
        bottomRemoved = true;
    }

    /**
     * @return is bottom view removed
     */
    public boolean isBottomRemoved() {
        return bottomRemoved;
    }

    /**
     * Set bottom view
     *
     * @param node to view
     */
    public void setBottom(Node node) {
        if (isBottomRemoved()) {
            splitPane.getItems().add(1, node);
        } else {
            splitPane.getItems().set(1, node);
        }
        bottomNode = node;
        bottomRemoved = false;
    }
}
