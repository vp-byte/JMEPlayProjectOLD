package com.jmeplay.plugin.assets;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.PathResolver;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldTreeCell;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create AssetsTreeCell to manage actions on tree cells
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class AssetsTreeCell extends TextFieldTreeCell<Path> {
    private ContextMenu contextMenu = null;
    private List<FileHandler> fileHandlers = null;

    public AssetsTreeCell(List<FileHandler> fileHandlers) {
        this.fileHandlers = fileHandlers;
        if (fileHandlers != null) {
            contextMenu = new ContextMenu();
            filterFileHandler(fileHandlers, this.getItem()).forEach(fileHandler -> {
                MenuItem menuItem = new MenuItem(fileHandler.name(), fileHandler.image());
                menuItem.setOnAction(event -> fileHandler.handle(this.getItem()));
                contextMenu.getItems().add(menuItem);
            });
        }
    }

    @Override
    public void updateItem(Path item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getFileName().toString());
        }
        if (!empty) {
            setContextMenu(contextMenu);
        }
    }

    private List<FileHandler> filterFileHandler(List<FileHandler> fileHandlers, Path path) {
        String fileExtension = PathResolver.resolveExtension(path);
        return fileHandlers.stream().filter(fileHandler -> {
            if (fileExtension != null && fileExtension.equals(fileHandler.filetype())) {
                return true;
            }
            if (fileHandler.filetype().equals("any")) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }
}