package com.jmeplay.core.gui;

import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.nio.file.Path;
import java.util.List;

/**
 * Abstract class to implement varies editor views
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public abstract class EditorViewer {

    /**
     * List with supported file types.
     * Please use file extensions to
     *
     * @return
     */
    public abstract List<String> filetypes();

    public abstract EditorViewerTab view(final Path path);

    public class EditorViewerTab extends Tab {
        private final Path path;

        public EditorViewerTab(final Path path, Node content) {
            this.path = path;
            this.setText(getTitle());
            this.setContent(content);
        }

        String getTitle() {
            return path.getFileName().toString();
        }

        public Path path() {
            return path;
        }

    }

}
