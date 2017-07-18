package com.jmeplay.core.handler;

import javafx.scene.image.ImageView;

import java.nio.file.Path;

/**
 * Interface to handle files and folders
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public interface FileHandler {

    /**
     * Use "any" as type for any file
     *
     * @return type of file extension
     */
    String filetype();

    /**
     * String value to represent handler in the GUI
     *
     * @return label to view in the GUI
     */
    String name();

    /**
     * String value to describe handler as a tooltip in the GUI
     *
     * @return description of the handler to view in the GUI
     */
    String description();

    /**
     * Image or icon to represent action of the handler
     *
     * @return image to view in the GUI
     */
    ImageView image();

    /**
     * Execute action on the file
     *
     * @param path to the file
     */
    void handle(Path path);
}
