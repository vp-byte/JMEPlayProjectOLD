package com.jmeplay.core.handler;

import javafx.scene.image.ImageView;

import java.nio.file.Path;
import java.util.List;

/**
 * Interface to handle filetypes and folders
 * any - for any type and folders
 * file - for any filetype only
 * folder - for any folder
 * fileextension - for specific file
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public abstract class FileHandler<T> {

    public static final String any = "any";
    public static final String file = "file";
    public static final String folder = "folder";

    /**
     * Use any - for any type and folders
     * Use file - for any filetype only
     * Use folder - for any folder
     * Use file extension - for specific file(txt for text file)
     *
     * @return type of filetypes extension
     */
    public abstract List<String> filetypes();

    /**
     * String value to represent handler in the GUI
     *
     * @return label to view in the GUI
     */
    public abstract String name();

    /**
     * String value to describe handler as a tooltip in the GUI
     *
     * @return description of the handler to view in the GUI
     */
    public abstract String description();

    /**
     * Image or icon to represent action of the handler
     *
     * @return image to view in the GUI
     */
    public abstract ImageView image();

    /**
     * Execute action on the file
     *
     * @param path to the file
     */
    public abstract void handle(Path path, T source);
}
