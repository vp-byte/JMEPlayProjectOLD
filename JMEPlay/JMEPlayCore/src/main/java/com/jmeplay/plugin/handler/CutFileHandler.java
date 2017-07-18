package com.jmeplay.plugin.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.image.ImageView;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Handler to cut file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 4)
public class CutFileHandler extends FileHandler {
    private int size = 24;

    @Override
    public String filetype() {
        return FileHandler.any;
    }

    @Override
    public String name() {
        return "Cut";
    }

    @Override
    public String description() {
        return "Cut file";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView("/icons/handler/cut.svg", size, size);
    }

    @Override
    public void handle(Path path) {
        System.out.println(path);
    }
}
