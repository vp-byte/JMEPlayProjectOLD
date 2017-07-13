package com.jmeplay.core.gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.springframework.stereotype.Component;

/**
 * Creates menu for the top view of editor
 * 
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderTopMenuBar {

	public MenuBar menuBar() {
		MenuBar menuBar = new MenuBar();

		// --- Menu File
		Menu menuFile = new Menu("File");

		// --- Menu Edit
		Menu menuEdit = new Menu("Edit");

		// --- Menu View
		Menu menuView = new Menu("View");

		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

		return menuBar;
	}

}
