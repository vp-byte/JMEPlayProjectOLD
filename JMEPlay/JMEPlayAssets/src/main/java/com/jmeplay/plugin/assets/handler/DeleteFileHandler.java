package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

/**
 * Handler to delete file from file system and tree view
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 6)
public class DeleteFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;
    private Path path = null;
    private TreeView<Path> source = null;

    @Autowired
    JMEPlayConsole jmePlayConsole;

    /**
     * {@link FileHandler:filetype}
     */
    @Override
    public String filetype() {
        return FileHandler.any;
    }

    /**
     * {@link FileHandler:name}
     */
    @Override
    public String name() {
        return "Delete";
    }

    /**
     * {@link FileHandler:description}
     */
    @Override
    public String description() {
        return "Delete file from project";
    }

    /**
     * {@link FileHandler:image}
     */
    @Override
    public ImageView image() {
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "delete.svg", size, size);
    }

    /**
     * Handle event to delete file
     *
     * @param path   to the file
     * @param source of tree view with path
     */
    @Override
    public void handle(Path path, TreeView<Path> source) {
        this.path = path;
        this.source = source;

        Optional<ButtonType> result = createConfirmAlert().showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                executeDelete();
                jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.SUCCESS, "Delete " + path + " from project");
            } catch (IOException ex) {
                jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.ERROR, "Delete " + path + " from project fail");
                jmePlayConsole.writeException(ex);
            }
        }
    }

    /**
     * Create alert to confirm file delete
     *
     * @return confirm alert
     */
    private Alert createConfirmAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText("Really delete " + path.getFileName() + "?");
        return alert;
    }

    /**
     * Delete execution
     *
     * @throws IOException if file do not exist
     */
    private void executeDelete() throws IOException {
        if (!Files.isDirectory(path)) {
            Files.delete(path);
        } else {
            Files.walkFileTree(path, new DeleteFileVisitor());
        }
        TreeItem<Path> treeItem = source.getSelectionModel().getSelectedItem();
        treeItem.getParent().getChildren().remove(treeItem);
    }

    /**
     * FileVisitor to delete files or folders
     */
    private class DeleteFileVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }
    }
}
