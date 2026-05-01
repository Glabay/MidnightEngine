package dev.midnightcoder.engine.scene;

import dev.midnightcoder.engine.renderer.Renderer;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public abstract class Scene {
    public void onLoad() {}
    public void update(double delta) {}
    public void render(Renderer renderer) {}
    public void onUnload() {}
}
