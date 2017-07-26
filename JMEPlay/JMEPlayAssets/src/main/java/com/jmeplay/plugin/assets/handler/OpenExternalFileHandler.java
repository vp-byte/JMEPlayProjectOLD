package com.jmeplay.plugin.assets.handler;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.plugin.assets.Resources;
import javafx.application.Platform;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler to open file in external editor
 * Support for mac, win, linux operating systems
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Order(value = 2)
public class OpenExternalFileHandler extends FileHandler<TreeView<Path>> {
    private int size = 24;
    private static String OS = System.getProperty("os.name").toLowerCase();

    @Autowired
    JMEPlayConsole jmePlayConsole;

    /**
     * {@link FileHandler:filetype}
     */
    @Override
    public String filetype() {
        return FileHandler.file;
    }


    /**
     * {@link FileHandler:name}
     */
    @Override
    public String name() {
        return "Open external";
    }


    /**
     * {@link FileHandler:description}
     */
    @Override
    public String description() {
        return "Open file in external editor";
    }


    /**
     * {@link FileHandler:image}
     */
    @Override
    public ImageView image() {
        return ImageLoader.initImageView(this.getClass(), Resources.iconsHandler + "penexternal.svg", size, size);
    }

    /**
     * Handle event to open file in external editor
     *
     * @param path   to the file
     * @param source of event
     */
    @Override
    public void handle(Path path, TreeView<Path> source) {
        Platform.runLater(new ProcessRunner(path));
    }

    private class ProcessRunner implements Runnable {
        Path path;

        public ProcessRunner(Path path) {
            this.path = path;
        }

        @Override
        public void run() {
            final List<String> commands = new ArrayList<>();
            if (isMac()) {
                commands.add("open");
            } else if (isWindows()) {
                commands.add("cmd");
                commands.add("/c");
                commands.add("start");
            } else if (isUnix()) {
                commands.add("xdg-open");
            }

            if (commands.isEmpty()) return;

            String url;
            try {
                url = path.toUri().toURL().toString();
            } catch (MalformedURLException ex) {
                jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.ERROR, "File " + path + " do not exist");
                jmePlayConsole.writeException(ex);
                return;
            }

            commands.add(url);

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(commands);

            try {
                Process process = processBuilder.start();
                Reader in = new InputStreamReader(process.getErrorStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(in);
                if (reader.lines() != null && reader.lines().count() > 0) {
                    throw new IllegalStateException(reader.readLine());
                }
            } catch (Exception ex) {
                jmePlayConsole.writeMessage(JMEPlayConsole.MessageType.ERROR, "Can't open file " + path);
                jmePlayConsole.writeException(ex);
            }
        }
    }

    /**
     * @return true if actual operating system windows
     */
    private boolean isWindows() {
        return (OS.contains("win"));
    }


    /**
     * @return true if actual operating system mac
     */
    private boolean isMac() {
        return (OS.contains("mac"));
    }


    /**
     * @return true if actual operating system linux or unix
     */
    private boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
}
