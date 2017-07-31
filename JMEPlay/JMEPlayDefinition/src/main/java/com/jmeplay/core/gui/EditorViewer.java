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

    public EditorViewerTab view(final Path path) {
        return new EditorViewerTab(path);
    }

    public class EditorViewerTab extends Tab {
        private final Path path;

        public EditorViewerTab(final Path path) {
            this.path = path;
            this.setText(getTitle());
        }

        public EditorViewerTab(String text, final Path path) {
            super(text);
            this.path = path;
        }

        public EditorViewerTab(String text, Node content, final Path path) {
            super(text, content);
            this.path = path;
        }

        public String getTitle() {
            return path.getFileName().toString();
        }

        public Path path() {
            return path;
        }
    }

}
