package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Handler to cut file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 4)
public class CutFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Autowired
    private JMEPlayConsole jmePlayConsole;

    @Override
    public List<String> filetypes() {
        return singletonList(FileHandler.any);
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
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "cut.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.SUCCESS, "Cut " + path + " to clipboard");

        ClipboardContent content = new ClipboardContent();
        content.putFiles(singletonList(path.toFile()));

        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
    }
}
