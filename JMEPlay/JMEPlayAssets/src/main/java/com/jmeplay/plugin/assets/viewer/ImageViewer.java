package com.jmeplay.plugin.assets.viewer;

import com.jmeplay.core.gui.EditorViewer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class to define new component in Editor on left, right, bottom side
 */
@Component
public class ImageViewer extends EditorViewer {

    @PostConstruct
    private void init() {
    }

    @Override
    public List<String> filetypes() {
        return Arrays.asList("jpg", "jpeg", "png", "svg");
    }

    @Override
    public EditorViewerTab view(final Path path) {
        EditorViewerTab tab = super.view(path);
        tab.setOnClosed(event -> tab.getTabPane().getTabs().remove(tab));
        return tab;
    }

}
