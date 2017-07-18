package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Builder to create top view of editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderTop {

    private VBox top;

    @Autowired
    private EditorBuilderTopMenuBar editorBuilderTopMenuBar;

    @Autowired
    private EditorBuilderTopToolBar editorBuilderTopToolBar;

    @PostConstruct
    private void init() {
        top = new VBox();
        top.getChildren().add(editorBuilderTopMenuBar.menuBar());
        top.getChildren().add(editorBuilderTopToolBar.getToolBar());
    }

    /**
     * Create top view for asset editor
     */
    public Node view() {
        return top;
    }

}
