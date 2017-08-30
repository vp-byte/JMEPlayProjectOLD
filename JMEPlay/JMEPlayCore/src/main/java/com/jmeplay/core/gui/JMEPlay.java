package com.jmeplay.core.gui;

import com.jmeplay.Resources;
import com.jmeplay.core.utils.Settings;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Screen;
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
    private Stage stage;

    @Value("${application.name}")
    private String applicationName;

    @Value("${jme.version}")
    private String jmeVersion;

    // Injected
    private Settings settings;
    private EditorBuilder editorBuilder;
    private EditorBuilderCenterCenter editorBuilderCenterCenter;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Autowired
    public void setEditorBuilder(EditorBuilder editorBuilder) {
        this.editorBuilder = editorBuilder;
    }

    @Autowired
    public void setEditorBuilderCenterCenter(EditorBuilderCenterCenter editorBuilderCenterCenter) {
        this.editorBuilderCenterCenter = editorBuilderCenterCenter;
    }

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
        this.stage = stage;
        initStage();

        stage.widthProperty().addListener((observable, oldValue, newValue) -> editorBuilderCenterCenter.resetDividerPositions());
        stage.heightProperty().addListener((observable, oldValue, newValue) -> editorBuilderCenterCenter.resetDividerPositions());
        //new Thread(() -> Platform.runLater(() -> showSplashscreen())).start();
    }

    private void initStage() {
        initStageTitle();
        stage.setScene(editorBuilder.getScene());


        initStageMinHeight();
        initStageMinWidth();
        initStageMaximized();

        stage.show();

        initStageY();
        initStageX();
    }

    private void initStageTitle() {
        stage.setTitle(applicationName + " (" + jmeVersion + ")");
    }

    private void initStageMinHeight() {
        Double stageMinHeight = settings.getOptionDouble(Resources.editorStageMinHeight, Resources.editorDefaultStageMinHeight);
        Double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        if (stageMinHeight == null) {
            stageMinHeight = screenHeight * 2 / 3;
            settings.setOption(Resources.editorStageMinHeight, stageMinHeight);
        }
        stage.setMinHeight(stageMinHeight);
    }

    private void initStageMinWidth() {
        Double stageMinWidth = settings.getOptionDouble(Resources.editorStageMinWidth, Resources.editorDefaultStageMinWidth);
        Double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        if (stageMinWidth == null) {
            stageMinWidth = screenWidth * 2 / 3;
            settings.setOption(Resources.editorStageMinWidth, stageMinWidth);
        }
        stage.setMinWidth(stageMinWidth);
    }

    private void initStageMaximized() {
        stage.setMaximized(settings.getOptionAsBoolean(Resources.editorMaximized, Resources.editorDefaultMaximized));
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> settings.setOption(Resources.editorMaximized, newValue));
    }

    private void initStageY() {
        Double stageY = settings.getOptionDouble(Resources.editorStageY, Resources.editorDefaultStageY);
        if (stageY != null) {
            stage.setY(stageY);
        }
        stage.yProperty().addListener((observable, oldValue, newValue) -> {
            if (!stage.isMaximized()) {
                settings.setOption(Resources.editorStageY, newValue.doubleValue());
            }
        });
    }

    private void initStageX() {
        Double stageX = settings.getOptionDouble(Resources.editorStageX, Resources.editorDefaultStageX);
        if (stageX != null) {
            double screensWidth = Screen.getScreens().stream().mapToDouble(screen -> screen.getBounds().getWidth()).sum();
            double stageXWidth = stageX + stage.getMinWidth();
            if (stageXWidth <= screensWidth) {
                stage.setX(stageX);
            }
        }
        stage.xProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!stage.isMaximized()) {
                settings.setOption(Resources.editorStageX, newValue.doubleValue());
            }
        });
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


    private void showSplashscreen() {
        Stage splashscreenStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        Image image = new Image(JMEPlay.class.getClass().getResourceAsStream("/images/splashscreen/JMEPlaySplashscreen.gif"));
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
        splashscreenStage.setScene(scene);
        splashscreenStage.setMaxHeight(image.getHeight());
        splashscreenStage.setMaxWidth(image.getWidth());
        splashscreenStage.initStyle(StageStyle.UNDECORATED);
        splashscreenStage.initModality(Modality.APPLICATION_MODAL);
        splashscreenStage.show();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                splashscreenStage.hide();
                stage.show();
            });
        }).start();

    }
}
