import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.Test;

public class TestCopy extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hallo");
        Button b = new Button("TEST");
        b.setOnMouseClicked(this::processClick);
        stage.setScene(new Scene(new BorderPane(b)));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {

        Application.launch(args);
    }

    private void processClick(final MouseEvent event) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.getFiles().forEach(file -> System.out.println(file));
        System.out.println(clipboard.hasContent(DataFormat.FILES));
    }
}
