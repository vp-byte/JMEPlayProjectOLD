package com.jmeplay.plugin.images;

import com.jmeplay.core.gui.EditorViewer;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation for image view in JMEPlay.
 * Supported file types are jpg, jpeg, png, svg
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayImageViewerComponent extends EditorViewer {
    private int tabIconSize;
    // Injected
    private Settings settings;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        initSettings();
    }

    /**
     * Initialize settings for image view tab
     */
    private void initSettings() {
        tabIconSize = settings.getOptionInteger(Resources.imageViewTabIconSize, Resources.imageViewDefaultTabIconSize);
    }

    @Override
    public List<String> filetypes() {
        return Arrays.asList("jpg", "jpeg", "png", "svg");
    }

    @Override
    public EditorViewerTab view(final Path path) {
        JMEPlayImageScrollPane imePLayImageScrollPane = new JMEPlayImageScrollPane(path);
        EditorViewerTab tab = new EditorViewerTab(path, imePLayImageScrollPane);
        tab.setGraphic(ImageLoader.initImageView(this.getClass(), Resources.iconsImagesImage, tabIconSize, tabIconSize));
        return tab;
    }
}
