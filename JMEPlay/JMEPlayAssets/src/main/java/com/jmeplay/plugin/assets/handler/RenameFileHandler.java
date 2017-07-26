package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.control.TreeView;
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
public class RenameFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Override
    public String filetype() {
        return FileHandler.any;
    }

    @Override
    public String name() {
        return "Rename";
    }

    @Override
    public String description() {
        return "Rename file / folder";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "rename.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        System.out.println(path);
    }
}
