package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.entity.Hitbox;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.Texture;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Mob extends Entity {
    protected final int moveSpeed = 1;

    protected int x;
    protected int y;
    protected int speed = 3;

    public Mob(int x, int y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox(x, y, width, height);
    }

    @Override
    public void render(Renderer renderer) {
        if (texture != null)
            renderer.renderTexture(texture, x, y);
    }

    @Override
    public void update(double delta) {
        updateHitbox();
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

    public void updateHitbox() {
        hitbox.updateHitbox(x, y);
    }
}
