package com.jmeplay.plugin.assets.gui;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.PathResolver;
import com.jmeplay.plugin.assets.handler.OpenFileHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Create AssetsTreeCell to manage actions on tree cells
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class AssetsTreeCell extends TextFieldTreeCell<Path> {
    private List<FileHandler<TreeView<Path>>> fileHandlers = null;
    private ContextMenu contextMenu = null;

    public AssetsTreeCell(List<FileHandler<TreeView<Path>>> fileHandlers) {
        this.fileHandlers = fileHandlers;

        // event handling
        setOnMouseClicked(this::processClick);
    }

    @Override
    public void updateItem(Path item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getFileName().toString());
        }
    }

    /**
     * Handle a mouse click.
     */
    private void processClick(final MouseEvent event) {
        if (getItem() == null) return;

        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();
        }

        final MouseButton button = event.getButton();

        if (button.equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                Optional<FileHandler<TreeView<Path>>> openFileHandler = filterFileHandler(fileHandlers).stream().filter(fileHandler -> fileHandler instanceof OpenFileHandler).findFirst();
                if (openFileHandler.isPresent()) {
                    openFileHandler.get().handle(getItem(), this.getTreeView());
                }
            }
        }

        if (button == MouseButton.SECONDARY) {
            contextMenu = updateContextMenu();
            if (contextMenu == null) return;
            if (!contextMenu.isShowing()) {
                contextMenu.show(this, Side.BOTTOM, event.getX(), -event.getY());
            }
        }
    }

    private ContextMenu updateContextMenu() {
        if (fileHandlers != null) {
            ContextMenu contextMenu = new ContextMenu();
            filterFileHandler(fileHandlers).forEach(fileHandler -> {
                MenuItem menuItem = new MenuItem(fileHandler.name(), fileHandler.image());
                menuItem.setOnAction(event -> fileHandler.handle(this.getItem(), this.getTreeView()));
                contextMenu.getItems().add(menuItem);
            });
            return contextMenu;
        }
        return null;
    }

    private List<FileHandler<TreeView<Path>>> filterFileHandler(List<FileHandler<TreeView<Path>>> fileHandlers) {
        String fileExtension = PathResolver.resolveExtension(getItem());
        return fileHandlers.stream().filter(fileHandler -> {
            for (String filetype : fileHandler.filetypes()) {
                if (fileExtension != null && fileExtension.equals(filetype)) {
                    return true;
                }
                if (filetype.equals(FileHandler.any)) {
                    return true;
                }
                if (getItem() != null && !Files.isDirectory(getItem()) && filetype.equals(FileHandler.file)) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}