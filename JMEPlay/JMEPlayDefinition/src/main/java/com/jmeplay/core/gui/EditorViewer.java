package com.jmeplay.core.gui;

import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.nio.file.Path;
import java.util.List;

/**
 * Inteface to implement new EditorView
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public abstract class EditorViewer {

    public abstract List<String> filetypes();

    public abstract EditorViewerTab view(final Path path);

    public class EditorViewerTab extends Tab {
        private final Path path;

        public EditorViewerTab(final Path path, Node content) {
            this.path = path;
            this.setText(getTitle());
            this.setContent(content);
        }

        public String getTitle() {
            return path.getFileName().toString();
        }

        public Path path() {
            return path;
        }

    }

}
