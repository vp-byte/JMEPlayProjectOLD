package com.jmeplay.plugin.assets.gui;

import com.jmeplay.core.EditorComponent;
import com.jmeplay.core.Position;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * Create AssetEditorComponent to load and view all assets in tree view
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class AssetsComponent extends EditorComponent {
    private final String uniqueId = "73a3b67a-d279-4d5e-9b83-852570cdc2a6";
    private final String name = "Assets";
    private final String description = "Component to manage all assets";
    private Label label;
    private StackPane component;

    private String rootFolder = "/home/vp-byte/jwGame/JWGame/assets/";

    private TreeView<Path> treeView;
    private TreeItem<Path> rootTreeItem;

    @Autowired
    private AssetsImageDefinder assetsImageDefinder;

    @Autowired(required = false)
    private List<FileHandler<TreeView<Path>>> fileHandlers;

    @PostConstruct
    private void init() {
        setPosition(Position.LEFT);
        label = new Label("Assets", ImageLoader.initImageView(this.getClass(), Resources.iconsAssets + "assets.svg", 32, 32));
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
        Path rootPath = Paths.get(rootFolder);
        rootTreeItem = new TreeItem<>(rootPath.getFileName());
        rootTreeItem.setExpanded(true);

        try {
            createTree(rootPath, rootTreeItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // sort tree structure by name
        rootTreeItem.getChildren().sort(Comparator.comparing(t -> t.getValue().getFileName().toString().toLowerCase()));

        // create components
        treeView = new TreeView<>(rootTreeItem);
        treeView.setCellFactory(param -> new AssetsTreeCell(fileHandlers));
    }

    /**
     * Recursively create the tree
     *
     * @param rootPath
     * @throws IOException
     */
    private void createTree(Path rootPath, TreeItem<Path> rootItem) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootPath)) {
            for (Path path : directoryStream) {
                TreeItem<Path> newItem = new TreeItem(path, assetsImageDefinder.imageByFilename(path));
                rootItem.getChildren().add(newItem);
                if (Files.isDirectory(path)) {
                    createTree(path, newItem);
                }
            }
            // sort tree structure by name
            rootItem.getChildren().sort(Comparator.comparing(t -> t.getValue().getFileName().toString().toLowerCase()));
        }
    }

}
