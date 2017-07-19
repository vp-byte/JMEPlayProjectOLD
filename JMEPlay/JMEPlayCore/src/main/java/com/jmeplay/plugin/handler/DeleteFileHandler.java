package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
    TreeView<Path> source = null;

    @Override
    public String filetype() {
        return FileHandler.any;
    }

    @Override
    public String name() {
        return "Delete";
    }

    @Override
    public String description() {
        return "Delete file from project";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView("/icons/handler/delete.svg", size, size);
    }

    /**
     * Handle event
     *
     * @param path   to the file
     * @param source of tree view with path
     */
    @Override
    public void handle(Path path, TreeView<Path> source) {
        this.path = path;
        this.source = source;

        Optional<ButtonType> result = createConfirmAlert().showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                executeDelete();
            } catch (IOException ex) {
                Alert alert = createErrorAlert(ex);
                alert.showAndWait();
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
        alert.setContentText("Really delete " + path.getFileName() + " file?");
        return alert;
    }

    /**
     * Delete execution
     *
     * @throws IOException
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
     * Create alert to show error dialog
     *
     * @param exception to show stack trace
     * @return error alert
     */
    private Alert createErrorAlert(IOException exception) {
        String stackTraceText = stackTraceToString(exception);
        TextArea textAreaStackTrace = textAreaStackTrace(stackTraceText);
        GridPane gridPane = gridPane(textAreaStackTrace);
        Alert alert = errorAlert(exception);
        alert.getDialogPane().setExpandableContent(gridPane);
        return alert;
    }

    /**
     * Convert exception stack trace to string
     *
     * @param exception to get stack trace
     * @return stack trace as string
     */
    private String stackTraceToString(IOException exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Create text area for stack trace text
     *
     * @param stackTraceText
     * @return text area with
     */
    private TextArea textAreaStackTrace(String stackTraceText) {
        TextArea textArea = new TextArea(stackTraceText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        return textArea;
    }

    /**
     * Create grid pane with text area
     *
     * @param textAreaStackTrace
     * @return grid pane
     */
    private GridPane gridPane(TextArea textAreaStackTrace) {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(textAreaStackTrace, 0, 0);
        return gridPane;
    }

    /**
     * Create error alert
     *
     * @param exception
     * @return intialized alert
     */
    private Alert errorAlert(IOException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText(exception.getLocalizedMessage());
        alert.getDialogPane().expandedProperty().addListener((l) -> {
            Platform.runLater(() -> {
                alert.getDialogPane().requestLayout();
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.sizeToScene();
            });
        });
        return alert;
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
