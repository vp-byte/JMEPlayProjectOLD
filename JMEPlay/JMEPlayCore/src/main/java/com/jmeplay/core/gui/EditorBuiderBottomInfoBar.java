package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmeplay.core.BottomInfoMessage;
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
public class EditorBuiderBottomInfoBar implements BottomInfoMessage {

    private boolean visible;
    private int size;
    private Label modeSwitcher;
    private Label infoLabel;
    private HBox bottomBox;

    @Autowired
    private Settings settings;

    /**
     * Initialize info bar of the editor
     */
    @PostConstruct
    private void init() {
        visible = true;
        size = settings.getOptionInteger("guiIconSize", 32);
        initModeSwitcher();
        initInfoLabel();
        initBottomBox();
    }

    /**
     * Initialize switcher between two modes visible and hidden border bars
     */
    private void initModeSwitcher() {
        modeSwitcher = new Label();
        modeSwitcher.setGraphic(updateModeSwitcherImage());
        modeSwitcher.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            visible = !visible;
            modeSwitcher.setGraphic(updateModeSwitcherImage());
        });
    }

    /**
     * Update image of info bar depends on mode visible or hidden
     *
     * @return actual image
     */
    private ImageView updateModeSwitcherImage() {
        String imagePath = "/icons/infobar/disable.svg";
        if (!visible) {
            imagePath = "/icons/infobar/enable.svg";
        }
        return ImageLoader.initImageView(this.getClass(), imagePath, size - 4, size - 4);
    }

    /**
     * Initialize info label
     */
    private void initInfoLabel() {
        infoLabel = new Label();
        infoLabel.getStyleClass().add("infobar-infolabel");
        writeInfoMessage("Info message");
    }

    /**
     * Initialize bottom view with mode switcher and info label
     */
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
     * Is visible view mode
     *
     * @return view mode
     */
    public boolean isVisible() {
        return visible;
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
