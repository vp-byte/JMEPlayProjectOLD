package com.jmeplay.plugin.assets;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.PathResolver;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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

        final MouseButton button = event.getButton();
        if (button == MouseButton.SECONDARY) {

            if (contextMenu != null && contextMenu.isShowing()) {
                contextMenu.hide();
            }

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
            if (fileExtension != null && fileExtension.equals(fileHandler.filetype())) {
                return true;
            }
            if (fileHandler.filetype().equals(FileHandler.any)) {
                return true;
            }
            if (getItem() != null && !Files.isDirectory(getItem()) && fileHandler.filetype().equals(FileHandler.file)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }
}