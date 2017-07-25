package com.jmeplay.core.gui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Start JMEPlay editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@SpringBootApplication
@ComponentScan({"com.jmeplay.core", "com.jmeplay.plugin"})
public class JMEPlay extends Application {

    private ConfigurableApplicationContext appContext;

    @Value("${application.name}")
    private String applicationName;

    @Value("${jme.version}")
    private String jmeVersion;

    @Autowired
    private EditorBuilder editorBuilder;

    /**
     * Main point to start whole Application
     *
     * @param args
     */
    public static void main(String[] args) {
        // Activate SVG support for javafx
        SvgImageLoaderFactory.install();

        Application.launch(args);
    }

    /**
     * Initialize spring context
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        appContext = SpringApplication.run(this.getClass());
        appContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    /**
     * Start as JavaFX application
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        String title = applicationName + " (" + jmeVersion + ")";
        stage.setTitle(title);
        stage.setScene(editorBuilder.getScene());
        stage.setMaximized(true);
        stage.show();
        editorBuilder.initEditor();
        //showSplashScreen();
    }

    /**
     * Stop spring context
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        appContext.stop();
    }

    private void showSplashScreen() {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        Image image = new Image(JMEPlay.class.getClass().getResourceAsStream("/images/plugin/assets/splashscreen/JMEPlaySplashscreen.gif"));

        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        stage.setScene(scene);
        stage.setMaxHeight(image.getHeight());
        stage.setMaxWidth(image.getWidth());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> stage.hide());
        }).start();

    }
}
