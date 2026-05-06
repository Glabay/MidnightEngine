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
    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected Hitbox hitbox;

    protected Texture texture;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox(x, y, width, height);
    }

    public abstract void update(double delta);

    public void render(Renderer renderer) {
        if (texture != null)
            renderer.renderTexture(texture, x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void updateHitbox() {
        hitbox.updateHitbox(x, y);
    }
}
