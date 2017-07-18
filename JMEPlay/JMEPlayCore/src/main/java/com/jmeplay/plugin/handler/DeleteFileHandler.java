package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.image.ImageView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Handler to delete file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 6)
public class DeleteFileHandler extends FileHandler {
    private int size = 24;

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

    @Override
    public void handle(Path path) {
        System.out.println(path);
    }
}
