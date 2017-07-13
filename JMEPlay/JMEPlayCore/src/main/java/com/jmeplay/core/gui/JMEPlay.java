package com.jmeplay.core.gui;

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
@ComponentScan({ "com.jmeplay.core", "com.jmeplay.plugin" })
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
	 * Initialize SpringContext
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
		stage.setMinWidth(800);
		stage.setMinHeight(600);
		stage.setMaximized(true);
		stage.show();
		editorBuilder.initEditor();
	}

	/**
	 * Stop SpringContext
	 *
	 * @throws Exception
	 */
	@Override
	public void stop() throws Exception {
		appContext.stop();
	}
}
