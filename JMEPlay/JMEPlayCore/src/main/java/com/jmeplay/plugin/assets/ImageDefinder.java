package com.jmeplay.plugin.assets;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.ImageLoader;

import javafx.scene.image.ImageView;

/**
 * Defines image from filename or class type
 *
 * @author vp-byte(Vladimir Petrenko)
 */
@Component
public class ImageDefinder {
    private int size = 16;

    public ImageView imageByFilename(Path path) {
        if (Files.isDirectory(path)) {
            return ImageLoader.initImageView("/icons/assets/folder.svg", size, size);
        }
        return null;
    }

}
