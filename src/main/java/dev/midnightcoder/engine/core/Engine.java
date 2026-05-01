package dev.midnightcoder.engine.core;

import dev.midnightcoder.engine.input.AWTInputManager;
import dev.midnightcoder.engine.renderer.java2d.Java2DRenderer;
import dev.midnightcoder.engine.window.GameWindow;
import dev.midnightcoder.engine.window.WindowConfig;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class Engine {

    public static void start(Game game) {
        var inputManager = new AWTInputManager();
        var gameWindow = new GameWindow("Midnight Engine",
            WindowConfig.getWindowWidth(),
            WindowConfig.getWindowHeight(),
            inputManager
        );
        var renderer = new Java2DRenderer(gameWindow.getCanvas());

        var loop = new GameLoop(game, renderer, inputManager);

        loop.start();
    }
}
