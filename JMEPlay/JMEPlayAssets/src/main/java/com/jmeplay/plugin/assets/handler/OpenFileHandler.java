package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.gui.EditorCenter;
import com.jmeplay.core.gui.EditorViewer;
import com.jmeplay.core.gui.EditorViewer.EditorViewerTab;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.PathResolver;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Handler to copy file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 0)
public class OpenFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;

    @Autowired
    private JMEPlayConsole jmePlayConsole;

    @Autowired
    private List<EditorViewer> editorViewers;

    @Autowired
    private EditorCenter editorCenter;

    @Override
    public List<String> filetypes() {
        return singletonList(FileHandler.file);
    }

    @Override
    public String name() {
        return "Open";
    }

    @Override
    public String description() {
        return "Open file";
    }

    @Override
    public ImageView image() {
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "open.svg", size, size);
    }

    @Override
    public void handle(Path path, TreeView<Path> source) {
        System.err.println(path);
        EditorViewerTab tabToSelect = tabExists(path);
        if (tabToSelect != null) {
            editorCenter.centerView().getSelectionModel().select(tabToSelect);
            return;
        }

        openEditorViewer(path);

        jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.ERROR, "Open file " + path + " not implemented");
    }

    private EditorViewerTab tabExists(final Path path) {
        for (Tab tab : editorCenter.centerView().getTabs()) {
            EditorViewerTab editorViewerTab = ((EditorViewerTab) tab);
            if (editorViewerTab.path().equals(path)) {
                return editorViewerTab;
            }
        }
        return null;
    }

    private void openEditorViewer(Path path) {
        String fileExtension = PathResolver.resolveExtension(path);
        for (EditorViewer editorViewer : editorViewers) {
            for (String filetype : editorViewer.filetypes()) {
                if (fileExtension.equals(filetype)) {
                    EditorViewerTab tab = editorViewer.view(path);
                    editorCenter.centerView().getTabs().add(tab);
                    editorCenter.centerView().getSelectionModel().select(tab);
                    return;
                }
            }
        }
    }
}
