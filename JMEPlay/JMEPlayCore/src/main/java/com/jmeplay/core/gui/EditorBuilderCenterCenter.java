package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

@Component
public class EditorBuilderCenterCenter {

	private TabPane centerTabPane;

	private Node leftNode;
	private boolean leftRemoved = false;
	private Node centerNode;
	private Node rightNode;
	private boolean rightRemoved = false;
	private Node bottomNode;
	private boolean bottomRemoved = false;

	private SplitPane topSplitPane;
	private SplitPane splitPane;
	private double dividerPositions[] = { 0.2, 0.8, 0.8 };

	@PostConstruct
	private void init() {
		centerTabPane = new TabPane();
		leftNode = new StackPane(new Text("Left"));
		centerNode = new StackPane(centerTabPane);
		rightNode = new StackPane(new Text("Right"));
		bottomNode = new StackPane(new Text("Bottom"));

		topSplitPane = new SplitPane();
		topSplitPane.setOrientation(Orientation.HORIZONTAL);
		topSplitPane.getItems().addAll(leftNode, centerNode, rightNode);

		splitPane = new SplitPane();
		splitPane.setOrientation(Orientation.VERTICAL);
		splitPane.getItems().addAll(topSplitPane, bottomNode);

		/*
		 * splitPane.widthProperty().addListener(new ChangeListener<Number>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Number> observable,
		 * Number oldValue, Number newValue) { resetDividerPositions(); // FIXME can't
		 * proper set the position of divider in SplitPane } });
		 */
	}

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

	public void removeLeft() {
		topSplitPane.getItems().remove(leftNode);
		leftRemoved = true;
	}

	public boolean isLeftRemoved() {
		return leftRemoved;
	}

	public void setLeft(Node node) {
		if (isLeftRemoved()) {
			topSplitPane.getItems().add(0, node);
		} else {
			topSplitPane.getItems().set(0, node);
		}
		leftNode = node;
		leftRemoved = false;
	}

	public void removeRight() {
		topSplitPane.getItems().remove(rightNode);
		rightRemoved = true;
	}

	public boolean isRightRemoved() {
		return rightRemoved;
	}

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

	public void removeBottom() {
		splitPane.getItems().remove(bottomNode);
		bottomRemoved = true;
	}

	public boolean isBottomRemoved() {
		return bottomRemoved;
	}

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
