package com.jmeplay.plugin.console;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.Settings;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation for area to view formated and styled console output
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleArea extends CodeArea {
    private int toolsIconSize;
    private ContextMenu codeAreaMenu;

    private final String ERROR_PATTERN = "\\[(ERROR)\\]\\s";
    private final String WARN_PATTERN = "\\[(WARN)\\]\\s";
    private final String INFO_PATTERN = "\\[(INFO)\\]\\s";
    private final String SUCCESS_PATTERN = "\\[(SUCCESS)\\]\\s";

    private final String[] PATTERNS = {
            "(?<ERROR>" + ERROR_PATTERN + ")",
            "(?<WARN>" + WARN_PATTERN + ")",
            "(?<INFO>" + INFO_PATTERN + ")",
            "(?<SUCCESS>" + SUCCESS_PATTERN + ")"
    };

    private final Pattern COMPILED_PATTERN = Pattern.compile(String.join("|", PATTERNS));

    // Injected
    private Settings settings;
    private JMEPlayConsoleToolBar jmePlayConsoleToolBar;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Autowired
    public void setJmePlayConsoleToolBar(JMEPlayConsoleToolBar jmePlayConsoleToolBar) {
        this.jmePlayConsoleToolBar = jmePlayConsoleToolBar;
    }

    /**
     * Initialize JMEPlayConsoleArea
     */
    @PostConstruct
    private void init() {
        initSettings();
        this.setEditable(false);
        this.setOnMouseClicked(this::processClick);
    }

    /**
     * Initialize settings for console tool bar
     */
    private void initSettings() {
        toolsIconSize = settings.getOptionInteger(Resources.consoleToolsIconSize, Resources.consoleDefaultToolsIconSize);
    }

    /**
     * Handle a mouse click.
     */
    private void processClick(final MouseEvent event) {

        if (codeAreaMenu != null && codeAreaMenu.isShowing()) {
            codeAreaMenu.hide();
        }

        final MouseButton button = event.getButton();
        if (button == MouseButton.SECONDARY) {
            codeAreaMenu = new ContextMenu();
            MenuItem codeAreaMenuCopy = new MenuItem("Copy", ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleCopy, toolsIconSize, toolsIconSize));
            codeAreaMenuCopy.setOnAction(eventCopy -> this.copy());
            MenuItem codeAreaMenuSelectAll = new MenuItem("Select All", ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleSelectAll, toolsIconSize, toolsIconSize));
            codeAreaMenuSelectAll.setOnAction(eventSelectAll -> {
                this.selectAll();
                jmePlayConsoleToolBar.updateButtons();
            });
            SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
            MenuItem codeAreaClearAll = new MenuItem("Clear All", ImageLoader.initImageView(this.getClass(), Resources.iconsConsoleDelete, toolsIconSize, toolsIconSize));
            codeAreaClearAll.setOnAction(eventClearAll -> this.clear());
            if (this.getSelectedText() != null && !this.getSelectedText().isEmpty()) {
                codeAreaMenu.getItems().add(codeAreaMenuCopy);
            }
            if (this.getText() != null && !this.getText().isEmpty()) {
                codeAreaMenu.getItems().add(codeAreaMenuSelectAll);
                codeAreaMenu.getItems().add(separatorMenuItem);
                codeAreaMenu.getItems().add(codeAreaClearAll);
            }
            codeAreaMenu.show(this, event.getScreenX(), event.getScreenY());
        }
    }

    /**
     * Write text to code area
     */
    void writeText(String message) {
        this.replaceText(message);
        this.setStyleSpans(0, computeHighlighting(this.getText()));
    }


    /**
     * Compute highlighting for code area
     *
     * @param text to highlight
     * @return styles for defined spans
     */
    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = COMPILED_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("ERROR") != null ? "error" :
                            matcher.group("WARN") != null ? "warn" :
                                    matcher.group("INFO") != null ? "info" :
                                            matcher.group("SUCCESS") != null ? "success" :
                                                    null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
