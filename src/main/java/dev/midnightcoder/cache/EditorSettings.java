package dev.midnightcoder.cache;

import javafx.scene.Scene;

import java.io.File;
import java.util.Objects;

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

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(EditorSettings.class.getResource("dark-theme.css")).toExternalForm());
    }
}
