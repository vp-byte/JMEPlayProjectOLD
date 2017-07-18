package com.jmeplay.core.utils;

import java.nio.file.Path;

public class PathResolver {
    public static String resolveExtension(Path path) {
        if (path == null) {
            return null;
        }
        String filename = path.getFileName().toString();
        int lastIndexOfDot = filename.lastIndexOf(".");
        if (lastIndexOfDot > 0) {
            return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
        }
        return null;
    }
}