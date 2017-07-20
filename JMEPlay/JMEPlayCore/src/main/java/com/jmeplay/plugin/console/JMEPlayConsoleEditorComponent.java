package com.jmeplay.plugin.console;

import com.jmeplay.core.EditorComponent;
import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.Position;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
public class JMEPlayConsoleEditorComponent extends EditorComponent implements JMEPlayConsole {
    private final String uniqueId = "81e5ad3a-7e83-4b90-b744-90161d7412bd";
    private final String name = "JMEPlayConsole";
    private final String description = "Component to magage console output";
    private Label label;
    private CodeArea codeArea;
    private StackPane stackPane;

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

    @PostConstruct
    private void init() {
        setPosition(Position.BOTTOM);
        label = new Label("JMEPlayConsole");
        codeArea = new CodeArea();
        codeArea.setEditable(false);

        stackPane = new StackPane(new VirtualizedScrollPane<>(codeArea));
        this.writeException(new IOException("Hallo"));
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
        return stackPane;
    }

    @Override
    public void writeMessage(MessageType messageType, String message) {
        String text = "\n[" + messageType.name() + "] : " + message;
        String textAreaText = codeArea.getText();
        codeArea.replaceText(text + textAreaText);
        codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
    }

    @Override
    public void writeException(Exception exception) {
        writeMessage(MessageType.ERROR, stackTraceToString(exception));
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
     * @param text
     * @return
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
