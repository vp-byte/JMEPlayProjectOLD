package com.jmeplay.plugin;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.jmeplay.core.gui.EditorComponent;
import com.jmeplay.core.gui.Position;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

@Component
public class PropertyEditorComponent extends EditorComponent {
	private final String uniqueId = "a9430687-7cf0-4667-ac20-626aa51203f0";
	private final String name = "Properties";
	private final String description = "Component to manage properties of specific selected item";
	private Label label;
	private StackPane component;

	@PostConstruct
	private void init() {
		setPosition(Position.RIGHT);
		label = new Label("Properties");
		component = new StackPane(new Text("Properties"));
	}

	@Override
	public String uniqueId() {
		return uniqueId;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Label label() {
		return label;
	}

	@Override
	public Node component() {
		return component;
	}

}
