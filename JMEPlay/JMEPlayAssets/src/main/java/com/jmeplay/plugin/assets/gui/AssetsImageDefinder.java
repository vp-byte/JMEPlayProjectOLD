package com.jmeplay.plugin.assets.gui;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.PathResolver;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Defines image from filename
 *
 * @author vp-byte(Vladimir Petrenko)
 */
@Component
public class AssetsImageDefinder {
    private int size = 24;
    private final String basepath = Resources.iconsAssets;

    public ImageView imageByFilename(Path path) {
        if (Files.isDirectory(path)) {
            return ImageLoader.initImageView(this.getClass(), basepath + "folder.svg", size, size);
        } else {
            String fileExtension = PathResolver.resolveExtension(path);
            if (fileExtension != null) {
                switch (fileExtension.toLowerCase()) {
                    case "j3o":
                        return ImageLoader.initImageView(this.getClass(), basepath + "monkey.svg", size, size);
                    case "png":
                    case "jpg":
                    case "jpeg":
                        return ImageLoader.initImageView(this.getClass(), basepath + "image.svg", size, size);
                    case "fnt":
                        return ImageLoader.initImageView(this.getClass(), basepath + "font.svg", size, size);
                    case "j3m":
                        return ImageLoader.initImageView(this.getClass(), basepath + "material.svg", size, size);
                    case "ogg":
                    case "wav":
                        return ImageLoader.initImageView(this.getClass(), basepath + "music.svg", size, size);
                }
            }
        }
        return ImageLoader.initImageView(this.getClass(), basepath + "file.svg", size, size);
    }

}
