package dev.midnightcoder.engine.core;

import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public interface Game {
    void init(InputManager input);
    void update(double delta);
    void render(Renderer renderer);
    void shutdown();
}
