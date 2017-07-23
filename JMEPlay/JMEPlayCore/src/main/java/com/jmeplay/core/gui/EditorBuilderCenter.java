package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Create center view for editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderCenter {

    private BorderPane centerBorderPane;

    @Autowired
    private EditorBuilderCenterBorderBar editorBuilderCenterBorderBar;

    @Autowired
    private EditorBuiderBottomInfoBar buiderBottomInfoBar;

    @Autowired
    private EditorBuilderCenterCenter editorBuilderCenterCenter;

    /**
     * Initialize center view of the editor
     */
    @PostConstruct
    private void init() {
        centerBorderPane = new BorderPane();
        buiderBottomInfoBar.getViewModeSwitcher().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            updateCenterBorderPane();
        });
        updateCenterBorderPane();
    }

    /**
     * Update border pane an set left, right, bottom and center view
     */
    private void updateCenterBorderPane() {
        if (buiderBottomInfoBar.isVisible()) {
            centerBorderPane.setLeft(editorBuilderCenterBorderBar.borderBarLeft());
            centerBorderPane.setRight(editorBuilderCenterBorderBar.borderBarRight());
            centerBorderPane.setBottom(editorBuilderCenterBorderBar.borderBarBottom());
            centerBorderPane.setCenter(editorBuilderCenterCenter.view());
        } else {
            centerBorderPane.getChildren().remove(editorBuilderCenterBorderBar.borderBarLeft());
            centerBorderPane.getChildren().remove(editorBuilderCenterBorderBar.borderBarRight());
            centerBorderPane.getChildren().remove(editorBuilderCenterBorderBar.borderBarBottom());
        }
    }

    /**
     * Create center view for editor
     *
     * @return
     */
    public Node view() {
        return centerBorderPane;
    }

}
