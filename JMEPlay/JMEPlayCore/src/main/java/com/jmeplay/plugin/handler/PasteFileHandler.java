package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ss.rlib.util.ClassUtils.unsafeCast;

/**
 * Handler to paste file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 5)
public class PasteFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Override
    public String filetype() {
        return FileHandler.any;
    }

    @Override
    public String name() {
        return "Paste";
    }

    @Override
    public String description() {
        return "Paste file to clipboard";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView("/icons/handler/paste.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard == null) return;

        final List<File> files = (List<File>) clipboard.getContent(DataFormat.FILES);
        if (files == null || files.isEmpty()) return;

        Map<Path, Path> pasteOrder = new HashMap<>();
        files.forEach(filepath -> {
            try {
                fillPasteOrder(filepath.toPath(), pasteOrder);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        pasteOrder.forEach((k, v) -> System.err.println(v));


        // Dateien einzeln kopieren und unter umstaenden umbenennen

    }

    private void fillPasteOrder(Path path, Map<Path, Path> pasteOrder) throws IOException {
        if (!Files.isDirectory(path)) {
            pasteOrder.put(path, path);
            return;
        }
        pasteOrder.put(path, path);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path subpath : directoryStream) {
                pasteOrder.put(subpath, subpath);
                if (Files.isDirectory(subpath)) {
                    fillPasteOrder(subpath, pasteOrder);
                }
            }
        }
    }
}
