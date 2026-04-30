package dev.midnightcoder.engine.window;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class WindowConfig {
    private static final int _tileSize = 32;
    private static final int scale = 2;

    private static final int TILE_SIZE = _tileSize * scale;

    private static final int _screenCols = 16;
    private static final int _screenRows = 12;

    private static final int SCREEN_WIDTH = _screenCols * TILE_SIZE;
    private static final int SCREEN_HEIGHT = _screenRows * TILE_SIZE;

    public static Dimension getScreenDimensions() {
        return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public static int getWindowWidth() {
        return SCREEN_WIDTH;
    }

    public static int getWindowHeight() {
        return SCREEN_HEIGHT;
    }
}
