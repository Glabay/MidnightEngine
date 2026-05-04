package dev.midnightcoder.engine.core;

import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class GameLoop implements Runnable {

    private static final double UPDATE_RATE = 60.0;
    private static final double UPDATE_INTERVAL = 1_000_000_000.0 / UPDATE_RATE;

    private volatile boolean running;

    private final Thread gameThread;
    private final Game game;
    private final Renderer renderer;
    private final KeyboardInputManager inputManager;
    private final AWTMouseInputHandler mouseManager;

    public GameLoop(Game game, Renderer renderer, KeyboardInputManager inputManager, AWTMouseInputHandler mouseManager) {
        this.game = game;
        this.renderer = renderer;
        this.inputManager = inputManager;
        this.mouseManager = mouseManager;
        this.gameThread = new Thread(this, "Midnight-Engine-Game-Thread");
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread.start();
    }

    public synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        game.init(inputManager, mouseManager);

        var delta = 0.0;
        var lastTime = System.nanoTime();
        var currentTime = 0L;

        while(running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / UPDATE_INTERVAL;
            lastTime = currentTime;

            if (delta >= 1) {
                game.update(delta);
                inputManager.update();
                renderer.begin();
                game.render(renderer);
                renderer.end();
                delta--;
            }
        }

        game.shutdown();
    }
}
