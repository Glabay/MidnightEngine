package dev.midnightcoder.engine.core;

import dev.midnightcoder.engine.input.InputManager;
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
    private final InputManager inputManager;

    public GameLoop(Game game, Renderer renderer, InputManager inputManager) {
        this.game = game;
        this.renderer = renderer;
        this.inputManager = inputManager;
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
        game.init(inputManager);

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
