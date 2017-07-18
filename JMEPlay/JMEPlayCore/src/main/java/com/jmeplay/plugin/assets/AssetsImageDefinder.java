package com.jmeplay.plugin.assets;

import java.nio.file.Files;
import java.nio.file.Path;

import com.jmeplay.core.utils.PathResolver;
import org.springframework.stereotype.Component;

import com.jmeplay.core.utils.ImageLoader;

import javafx.scene.image.ImageView;

/**
 * Defines image from filename
 *
 * @author vp-byte(Vladimir Petrenko)
 */
@Component
public class AssetsImageDefinder {
    private int size = 24;

    public ImageView imageByFilename(Path path) {
        if (Files.isDirectory(path)) {
            return ImageLoader.initImageView("/icons/assets/folder.svg", size, size);
        } else {
            String fileExtension = PathResolver.resolveExtension(path);
            if (fileExtension != null) {
                switch (fileExtension.toLowerCase()) {
                    case "j3o":
                        return ImageLoader.initImageView("/icons/assets/monkey.svg", size, size);
                    case "png":
                    case "jpg":
                    case "jpeg":
                        return ImageLoader.initImageView("/icons/assets/image.svg", size, size);
                    case "fnt":
                        return ImageLoader.initImageView("/icons/assets/font.svg", size, size);
                    case "j3m":
                        return ImageLoader.initImageView("/icons/assets/material.svg", size, size);
                    case "ogg":
                    case "wav":
                        return ImageLoader.initImageView("/icons/assets/music.svg", size, size);
                }
            }
        }
        return ImageLoader.initImageView("/icons/assets/file.svg", size, size);
    }

}
