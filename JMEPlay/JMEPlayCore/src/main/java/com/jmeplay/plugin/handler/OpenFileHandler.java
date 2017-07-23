package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 1)
public class OpenFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Override
    public String filetype() {
        return FileHandler.file;
    }

    @Override
    public String name() {
        return "Open";
    }

    @Override
    public String description() {
        return "Open file";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView("/icons/handler/open.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        System.out.println(path);
    }
}
