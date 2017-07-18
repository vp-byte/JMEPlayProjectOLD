package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
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
@Order(value = 2)
public class CopyFileHandler implements FileHandler {
    private int size = 24;

    @Override
    public String filetype() {
        return "any";
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
    public void handle(Path path) {
        System.out.println(path);
    }
}
