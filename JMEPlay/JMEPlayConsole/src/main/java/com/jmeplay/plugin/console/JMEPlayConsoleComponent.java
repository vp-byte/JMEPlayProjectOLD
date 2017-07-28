package com.jmeplay.plugin.console;

import com.jmeplay.core.EditorComponent;
import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.Position;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;

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
    private StringBuilder stringBuilder = new StringBuilder();
    private Label label;
    private StackPane stackPane;
    private BorderPane borderPane;

    @Autowired
    private JMEPlayConsoleArea jmePlayConsoleArea;

    @Autowired
    private JMEPlayConsoleToolBar jmePlayConsoleToolBar;

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        setPosition(Position.BOTTOM);
        label = new Label("Console");

        stackPane = new StackPane(new VirtualizedScrollPane<>(jmePlayConsoleArea));
        stackPane.getStylesheets().add(getClass().getResource(Resources.cssConsole).toExternalForm());

        borderPane = new BorderPane();
        borderPane.setLeft(jmePlayConsoleToolBar);
        borderPane.setCenter(stackPane);
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
        return borderPane;
    }

    /**
     * {@link JMEPlayConsole:writeText}
     */
    @Override
    public void writeMessage(MessageType messageType, String message) {
        String text = "\n[" + messageType.name() + "] : " + message;
        stringBuilder.insert(0, text);
        jmePlayConsoleArea.writeText(stringBuilder.toString());
        jmePlayConsoleToolBar.updateButtons();
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
     * Clear text buffer
     */
    public void clear() {
        stringBuilder = new StringBuilder();
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

}
