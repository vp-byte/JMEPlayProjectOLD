package com.jmeplay.core.gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create menu for the top view of editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderTopMenuBar {
    MenuBar menuBar = null;

    /**
     * Initialize menu bar view depends on spring context
     */
    @PostConstruct
    private void init() {
        menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
    }

    /**
     * @return menu bar
     */
    public MenuBar menuBar() {
        return menuBar;
    }

}
