package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Create whole GUI and load all components and plugins
 * 
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilder {

	private Group root;
	private Scene scene;
	private BorderPane borderPane;

	@Autowired
	private EditorBuilderTop editorBuilderTop;

	@Autowired
	private EditorBuilderCenter editorBuilderCenter;

	@Autowired
	private EditorBuiderBottomInfoBar editorBuiderBottomInfoBar;

	@PostConstruct
	private void init() {
		root = new Group();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/editor/css/editor.css").toExternalForm());
		initBorderPane();
	}

	private void initBorderPane() {
		borderPane = new BorderPane();
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());
	}

	/**
	 * Initialize editor view
	 */
	public void initEditor() {
		borderPane.setTop(editorBuilderTop.view());
		borderPane.setCenter(editorBuilderCenter.view());
		borderPane.setBottom(editorBuiderBottomInfoBar.view());

		root.getChildren().addAll(borderPane);
	}

	/**
	 * @return scene for editor
	 */
	public Scene getScene() {
		return scene;
	}

}
