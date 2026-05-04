package dev.midnightcoder.engine.core;

import dev.midnightcoder.engine.input.keyboard.AWTKeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
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
        var inputManager = new AWTKeyboardInputManager();
        var mouseManager = new AWTMouseInputHandler();
        var gameWindow = new GameWindow("Midnight Engine",
            WindowConfig.getWindowWidth(),
            WindowConfig.getWindowHeight(),
            inputManager
        );
        var renderer = new Java2DRenderer(gameWindow.getCanvas());

        var loop = new GameLoop(game, renderer, inputManager, mouseManager);

        loop.start();
    }
}
