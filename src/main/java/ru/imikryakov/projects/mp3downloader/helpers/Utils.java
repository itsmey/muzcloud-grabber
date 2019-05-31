package ru.imikryakov.projects.mp3downloader.helpers;

import java.io.File;

public class Utils {
    public static File combinePaths(String parentDir, String path) {
        if (parentDir == null || parentDir.trim().isEmpty()) {
            return new File(path);
        } else {
            return new File(parentDir, path);
        }
    }

    public static String prepareFileName(String name) {
        return name.replaceAll("[\\\\\\/:?*\"<>|+]", "").trim();
    }
}
