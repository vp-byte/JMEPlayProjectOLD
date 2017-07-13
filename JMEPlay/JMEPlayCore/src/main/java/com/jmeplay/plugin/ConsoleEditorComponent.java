package com.jmeplay.plugin;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.jmeplay.core.EditorComponent;
import com.jmeplay.core.Position;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

@Component
public class ConsoleEditorComponent extends EditorComponent {
	private final String uniqueId = "81e5ad3a-7e83-4b90-b744-90161d7412bd";
	private final String name = "Console";
	private final String description = "Component to magage console output";
	private Label label;
	private StackPane component;

	@PostConstruct
	private void init() {
		setPosition(Position.BOTTOM);
		label = new Label("Console");
		component = new StackPane(new Text("Console"));
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
