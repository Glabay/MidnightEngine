package dev.midnightcoder.engine.entity;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.Texture;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public abstract class Entity {
    protected int width;
    protected int height;

    protected Hitbox hitbox;

    protected Texture texture;

    public abstract void update(double delta);

    public void render(Renderer renderer) {}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setHitboxDimension(int width, int height) {
        hitbox.setHitboxDimension(width, height);
    }
}
