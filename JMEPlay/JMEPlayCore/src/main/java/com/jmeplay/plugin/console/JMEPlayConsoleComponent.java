package com.jmeplay.plugin.console;

import com.jmeplay.core.EditorComponent;
import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.Position;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation for console in JMEPlay
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleComponent extends EditorComponent implements JMEPlayConsole {
    private final String uniqueId = "81e5ad3a-7e83-4b90-b744-90161d7412bd";
    private final String name = "Console";
    private final String description = "Component to magage console output";
    private boolean writeException = false;
    private Label label;
    private CodeArea codeArea;
    private StackPane stackPane;
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

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        setPosition(Position.BOTTOM);
        label = new Label("Console");
        initCodeArea();
        stackPane = new StackPane(new VirtualizedScrollPane<>(codeArea));
        stackPane.getStylesheets().add(getClass().getResource("/jmeplay/css/plugin/jmeplay-console.css").toExternalForm());
    }

    /**
     * {@link EditorComponent:uniqueId}
     */
    @Override
    public String uniqueId() {
        return uniqueId;
    }

    /**
     * {@link EditorComponent:name}
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * {@link EditorComponent:description}
     */
    @Override
    public String description() {
        return description;
    }

    /**
     * {@link EditorComponent:label}
     */
    @Override
    public Label label() {
        return label;
    }

    /**
     * {@link EditorComponent:component}
     */
    @Override
    public Node component() {
        return stackPane;
    }

    /**
     * {@link JMEPlayConsole:writeMessage}
     */
    @Override
    public void writeMessage(MessageType messageType, String message) {
        String text = "\n[" + messageType.name() + "] : " + message;
        String textAreaText = codeArea.getText();
        codeArea.replaceText(text + textAreaText);
        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
    }

    /**
     * {@link JMEPlayConsole:writeException}
     */
    @Override
    public void writeException(Exception exception) {
        if (writeException) {
            writeMessage(MessageType.ERROR, stackTraceToString(exception));
        }
    }

    /**
     * Initialize code area and do not editable
     */
    private void initCodeArea() {
        codeArea = new CodeArea();
        codeArea.setEditable(false);
        codeArea.setOnMouseClicked(this::processClick);
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
            MenuItem codeAreaMenuCopy = new MenuItem("Copy");
            codeAreaMenuCopy.setOnAction(eventCopy -> codeArea.copy());
            MenuItem codeAreaMenuSelectAll = new MenuItem("Select All");
            codeAreaMenuSelectAll.setOnAction(eventSelectAll -> codeArea.selectAll());
            if (codeArea.getSelectedText() != null && !codeArea.getSelectedText().isEmpty()) {
                codeAreaMenu.getItems().add(codeAreaMenuCopy);
            }
            if (codeArea.getText() != null && !codeArea.getText().isEmpty()) {
                codeAreaMenu.getItems().add(codeAreaMenuSelectAll);
            }
            codeAreaMenu.show(codeArea, event.getScreenX(), event.getScreenY());
        }
    }

    /**
     * Convert exception stack trace to string
     *
     * @param exception to get stack trace
     * @return stack trace as string
     */
    private String stackTraceToString(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
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
