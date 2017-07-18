package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;

/**
 * Creates tool bar for the top view of editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderTopToolBar {

    private ToolBar toolBar;

    @PostConstruct
    private void init() {
        toolBar = new ToolBar(new Button("New"), new Button("Open"), new Button("Save"),
                new Separator(Orientation.VERTICAL), new Button("Clean"), new Button("Compile"), new Button("Run"),
                new Separator(Orientation.VERTICAL), new Button("Debug"), new Button("Profile"));
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public void updateToolbar() {

    }
}
