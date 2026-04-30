package dev.midnightcoder.engine.renderer;

import dev.midnightcoder.engine.renderer.graphics.Texture;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public interface Renderer {
    void begin();
    void end();
    void update();

    void renderTexture(Texture texture, int x, int y);
}
