package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OpenTextFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Autowired
    private JMEPlayConsole jmePlayConsole;

    @Override
    public String filetype() {
        return FileHandler.file;
    }

    @Override
    public String name() {
        return "Open in Texteditor";
    }

    @Override
    public String description() {
        return "Open file in Texteditor";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "openastxt.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.ERROR, "Open file " + path + " in text editor");
    }
}
