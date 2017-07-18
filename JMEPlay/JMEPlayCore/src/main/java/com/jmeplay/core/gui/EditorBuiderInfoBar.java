package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.InfoMessage;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.Settings;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Create info bar for editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuiderInfoBar implements InfoMessage {

    private boolean expanded;
    private int size;
    private Label modeSwitcher;
    private Label infoLabel;
    private HBox bottomBox;

    @Autowired
    private Settings settings;

    @PostConstruct
    private void init() {
        expanded = true;
        size = settings.getOptionInteger("guiIconSize");
        initModeSwitcher();
        initInfoLabel();
        initBottomBox();
    }

    private void initModeSwitcher() {
        modeSwitcher = new Label();
        modeSwitcher.setGraphic(updateModeSwitcherImage());
        modeSwitcher.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            expanded = !expanded;
            modeSwitcher.setGraphic(updateModeSwitcherImage());
        });
    }

    private ImageView updateModeSwitcherImage() {
        String imagePath = "/icons/infobar/disable.svg";
        if (!expanded) {
            imagePath = "/icons/infobar/enable.svg";
        }
        return ImageLoader.initImageView(imagePath, size - 4, size - 4);
    }

    private void initInfoLabel() {
        infoLabel = new Label();
        infoLabel.getStyleClass().add("infobar-infolabel");
        writeInfoMessage("Info message");
    }

    private void initBottomBox() {
        bottomBox = new HBox();
        bottomBox.getStyleClass().add("infobar-bottom-box");
        bottomBox.setMinHeight(size);
        bottomBox.setMaxHeight(size);
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        bottomBox.getChildren().addAll(modeSwitcher, infoLabel);
    }

    /**
     * Get bottom view for editor
     *
     * @return HBox
     */
    public HBox view() {
        return bottomBox;
    }

    /**
     * Is expanded view mode
     *
     * @return view mode
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * Control to handle vie mode
     *
     * @return view mode switcher
     */
    public Label getViewModeSwitcher() {
        return modeSwitcher;
    }

    /**
     * Set message to bottom info bar
     */
    @Override
    public void writeInfoMessage(String message) {
        this.infoLabel.setText(message);
    }

    /**
     * Get message to bottom info bar
     */
    @Override
    public String infoMessage() {
        return infoLabel.getText();
    }
}
