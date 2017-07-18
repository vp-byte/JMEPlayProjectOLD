package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.image.ImageView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Handler to paste file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 5)
public class PasteFileHandler extends FileHandler {
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
    public void handle(Path path) {
        System.out.println(path);
    }
}
