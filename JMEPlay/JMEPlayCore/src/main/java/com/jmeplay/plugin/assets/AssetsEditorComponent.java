package com.jmeplay.plugin.assets;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.EditorComponent;
import com.jmeplay.core.Position;
import com.jmeplay.core.utils.ImageLoader;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

/**
 * Create AssetEditorComponent to load and view all assets in tree view
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class AssetsEditorComponent extends EditorComponent {
    private final String uniqueId = "73a3b67a-d279-4d5e-9b83-852570cdc2a6";
    private final String name = "Assets";
    private final String description = "Component to magage all assets";
    private Label label;
    private StackPane component;

    private String rootFolder = "/home/vp-byte/jwGame/JWGame/assets/";

    private TreeView<Path> treeView;
    private TreeItem<Path> rootTreeItem;

    @Autowired
    private ImageDefinder imageDefinder;

    @PostConstruct
    private void init() {
        setPosition(Position.LEFT);
        label = new Label("Assets", ImageLoader.initImageView("/icons/assets/assets.svg", 32, 32));
        initTreeView();
        component = new StackPane(treeView);
    }

    @Override
    public String uniqueId() {
        return uniqueId;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Label label() {
        return label;
    }

    @Override
    public Node component() {
        return component;
    }

    private void initTreeView() {
        rootTreeItem = new TreeItem<Path>(Paths.get(rootFolder));
        rootTreeItem.setExpanded(true);
        try {
            createTree(rootTreeItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // sort tree structure by name
        rootTreeItem.getChildren().sort(Comparator.comparing(new Function<TreeItem<Path>, String>() {
            @Override
            public String apply(TreeItem<Path> t) {
                return t.getValue().toString().toLowerCase();
            }
        }));

        // create components
        treeView = new TreeView<Path>(rootTreeItem);
    }

    /**
     * Recursively create the tree
     *
     * @param rootItem
     * @throws IOException
     */
    private void createTree(TreeItem<Path> rootItem) throws IOException {

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue())) {

            for (Path path : directoryStream) {

                TreeItem<Path> newItem = new TreeItem<Path>(path, imageDefinder.imageByFilename(path));
                newItem.setExpanded(true);

                rootItem.getChildren().add(newItem);

                if (Files.isDirectory(path)) {
                    createTree(newItem);
                }
            }
        }
    }

}
