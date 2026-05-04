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
public interface Game {
    void init(KeyboardInputManager input, AWTMouseInputHandler mouse);
    void update(double delta);
    void render(Renderer renderer);
    void shutdown();
}
