package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Creates center view for editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderCenter {

    private BorderPane centerBorderPane;

    @Autowired
    private EditorBuilderCenterBorderBar editorBuilderCenterBorderBar;

    @Autowired
    private EditorBuiderInfoBar buiderBottomInfoBar;

    @Autowired
    private EditorBuilderCenterCenter editorBuilderCenterCenter;

    @PostConstruct
    private void init() {
        centerBorderPane = new BorderPane();
        buiderBottomInfoBar.getViewModeSwitcher().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            initCenterBorderPane();
        });
        initCenterBorderPane();
    }

    private void initCenterBorderPane() {
        if (buiderBottomInfoBar.isExpanded()) {
            centerBorderPane.setLeft(editorBuilderCenterBorderBar.borderBarLeft());
            centerBorderPane.setRight(editorBuilderCenterBorderBar.borderBarRight());
            centerBorderPane.setBottom(editorBuilderCenterBorderBar.borderBarBottom());
            centerBorderPane.setCenter(editorBuilderCenterCenter.view());
        } else {
            centerBorderPane.setLeft(null);
            centerBorderPane.setRight(null);
            centerBorderPane.setBottom(null);
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
