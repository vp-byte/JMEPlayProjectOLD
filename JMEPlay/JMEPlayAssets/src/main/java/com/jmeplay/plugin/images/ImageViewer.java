package com.jmeplay.plugin.images;

import com.jmeplay.core.gui.EditorViewer;
import com.jmeplay.core.utils.ImageLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class to define new component in Editor on left, right, bottom side
 */
@Component
public class ImageViewer extends EditorViewer {
    private int size = 16;

    @Override
    public List<String> filetypes() {
        return Arrays.asList("jpg", "jpeg", "png", "svg");
    }

    @Override
    public EditorViewerTab view(final Path path) {
        ImageScrollPane imageScrollPane = new ImageScrollPane(path);
        EditorViewerTab tab = new EditorViewerTab(path, imageScrollPane);
        tab.setGraphic(ImageLoader.initImageView(this.getClass(), Resources.iconsImagesImage, size, size));
        return tab;
    }
}
