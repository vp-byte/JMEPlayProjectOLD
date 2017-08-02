package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.Settings;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Implementation of tool bar for console
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@SuppressWarnings("FieldCanBeLocal")
public class JMEPlayConsoleToolBar extends VBox {
    private int toolsSpacing;
    private int toolsIconSize;
    private Button buttonClose;
    private Button buttonCopy;
    private Button buttonSelectAll;
    private Button buttonClear;

    // Injected
    private Settings settings;
    private JMEPlayConsoleArea jmePlayConsoleArea;
    private JMEPlayConsoleComponent jmePlayConsoleComponent;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Autowired
    public void setJmePlayConsoleArea(JMEPlayConsoleArea jmePlayConsoleArea) {
        this.jmePlayConsoleArea = jmePlayConsoleArea;
    }

    @Autowired
    public void setJmePlayConsoleComponent(JMEPlayConsoleComponent jmePlayConsoleComponent) {
        this.jmePlayConsoleComponent = jmePlayConsoleComponent;
    }

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        initSettings();
        setSpacing(toolsSpacing);
        initButtonClose();
        initButtonCopy();
        initButtonClear();
        initButtonSelectAll();

        jmePlayConsoleArea.setOnMouseReleased(event -> updateButtons());
    }

    /**
     * Initialize settings for console tool bar
     */
    private void initSettings() {
        toolsSpacing = settings.getOptionInteger(Resources.consoleToolsSpacing, Resources.consoleDefaultToolsSpacing);
        toolsIconSize = settings.getOptionInteger(Resources.consoleToolsIconSize, Resources.consoleDefaultToolsIconSize);
        System.out.println(toolsIconSize);
    }

    /**
     * Initialize close button
     */
    private void initButtonClose() {
        buttonClose = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleClose, toolsIconSize, toolsIconSize));
        buttonClose.setTooltip(new Tooltip("Close console view"));
        buttonClose.setOnAction(event -> {
            MouseEvent mouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1,
                    true, true, true, true, true, true, true, true, true, true, null);
            jmePlayConsoleComponent.label().fireEvent(mouseEvent);
        });
        getChildren().add(buttonClose);
    }

    /**
     * Initialize copy button
     */
    private void initButtonCopy() {
        buttonCopy = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleCopy, toolsIconSize, toolsIconSize));
        buttonCopy.setTooltip(new Tooltip("Copy selected text"));
        buttonCopy.setDisable(true);
        buttonCopy.setOnAction(event -> {
            jmePlayConsoleArea.copy();
            updateButtons();
        });
        getChildren().add(buttonCopy);
    }

    /**
     * Initialize select all button
     */
    private void initButtonSelectAll() {
        buttonSelectAll = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleSelectAll, toolsIconSize, toolsIconSize));
        buttonSelectAll.setTooltip(new Tooltip("Select whole text"));
        buttonSelectAll.setDisable(true);
        buttonSelectAll.setOnAction(event -> {
            jmePlayConsoleArea.selectAll();
            updateButtons();
        });
        getChildren().add(buttonSelectAll);
        updateButtons();
    }

    /**
     * Initialize clear button
     */
    private void initButtonClear() {
        buttonClear = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleDelete, toolsIconSize, toolsIconSize));
        buttonClear.setTooltip(new Tooltip("Clear whole text"));
        buttonClear.setDisable(true);
        buttonClear.setOnAction(event -> {
            jmePlayConsoleComponent.clear();
            jmePlayConsoleArea.clear();
            updateButtons();
        });
        getChildren().add(buttonClear);
    }

    /**
     * Update view of buttons
     */
    void updateButtons() {
        boolean containsText = jmePlayConsoleArea.getText() != null && !jmePlayConsoleArea.getText().isEmpty();
        buttonSelectAll.setDisable(!containsText);
        buttonClear.setDisable(!containsText);

        boolean textSelected = jmePlayConsoleArea.getSelectedText() != null && !jmePlayConsoleArea.getSelectedText().isEmpty();
        buttonCopy.setDisable(!textSelected);
    }
}
