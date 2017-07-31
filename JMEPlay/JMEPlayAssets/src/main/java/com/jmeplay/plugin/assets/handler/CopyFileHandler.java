package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.util.Collections.singletonList;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 3)
public class CopyFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Autowired
    private JMEPlayConsole jmePlayConsole;

    @Override
    public List<String> filetypes() {
        return singletonList(FileHandler.any);
    }

    @Override
    public String name() {
        return "Copy";
    }

    @Override
    public String description() {
        return "Copy file to clipboard";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "copy.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.SUCCESS, "Copy " + path + " to clipboard");

        ClipboardContent content = new ClipboardContent();
        content.putFiles(singletonList(path.toFile()));

        Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.setContent(content);
    }

}
