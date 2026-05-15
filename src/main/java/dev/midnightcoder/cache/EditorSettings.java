package dev.midnightcoder.cache;

import java.io.File;

public class EditorSettings {
    private static File lastOpenedDirectory = new File(System.getProperty("user.dir"));

    public static File getLastOpenedDirectory() {
        if (lastOpenedDirectory != null && !lastOpenedDirectory.exists()) {
            lastOpenedDirectory = new File(System.getProperty("user.dir"));
        }
        return lastOpenedDirectory;
    }

    public static void setLastOpenedDirectory(File directory) {
        if (directory != null && directory.isDirectory()) {
            lastOpenedDirectory = directory;
        }
    }
}
