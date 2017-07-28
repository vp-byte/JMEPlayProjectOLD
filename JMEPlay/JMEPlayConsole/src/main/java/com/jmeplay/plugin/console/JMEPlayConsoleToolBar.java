package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ImageLoader;
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
public class JMEPlayConsoleToolBar extends VBox {
    private int size = 24;
    private Button buttonClose;
    private Button buttonCopy;
    private Button buttonSelectAll;
    private Button buttonClear;

    @Autowired
    private JMEPlayConsoleArea jmePlayConsoleArea;

    @Autowired
    private JMEPlayConsoleComponent jmePlayConsoleComponent;

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        setSpacing(5);
        initButtonClose();
        initButtonCopy();
        initButtonClear();
        initButtonSelectAll();

        jmePlayConsoleArea.setOnMouseReleased(this::mouseEventUpdateButtons);
    }

    /**
     * Update buttons on tool bar
     *
     * @param event from mouse
     */
    private void mouseEventUpdateButtons(MouseEvent event) {
        updateButtons();
    }

    /**
     * Initialize close button
     */
    private void initButtonClose() {
        buttonClose = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleClose, size, size));
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
        buttonCopy = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleCopy, size, size));
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
        buttonSelectAll = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleSelectAll, size, size));
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
        buttonClear = new Button(null, ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleDelete, size, size));
        buttonClear.setTooltip(new Tooltip("Clear whole text"));
        buttonClear.setDisable(true);
        buttonClear.setOnAction(event -> {
            jmePlayConsoleArea.clear();
            updateButtons();
        });
        getChildren().add(buttonClear);
    }

    /**
     * Update view of buttons
     */
    public void updateButtons() {
        boolean containsText = jmePlayConsoleArea.getText() != null && !jmePlayConsoleArea.getText().isEmpty();
        buttonSelectAll.setDisable(!containsText);
        buttonClear.setDisable(!containsText);

        boolean textSelected = jmePlayConsoleArea.getSelectedText() != null && !jmePlayConsoleArea.getSelectedText().isEmpty();
        buttonCopy.setDisable(!textSelected);
    }
}
