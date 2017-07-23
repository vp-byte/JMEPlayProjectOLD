package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static java.util.Collections.singletonList;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 3)
public class CopyFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Override
    public String filetype() {
        return FileHandler.any;
    }

    @Override
    public String name() {
        return "Copy";
    }

    @Override
    public String description() {
        return "Copy file to clipboard";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView("/icons/handler/copy.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        final ClipboardContent content = new ClipboardContent();
        content.putFiles(singletonList(path.toFile()));
        content.put(DataFormat.FILES, "copy");

        final Clipboard clipboard = Clipboard.getSystemClipboard();

        clipboard.setContent(content);
    }
}
