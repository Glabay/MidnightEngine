package dev.midnightcoder.engine.entity;

import dev.midnightcoder.engine.util.Vec2i;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class Hitbox {
    private final Rectangle rectangle;
    private final Vec2i offset;

    public Hitbox(int x, int y, int width, int height) {
        this.rectangle = new Rectangle(x, y, width, height);
        this.offset = new Vec2i(0, 0);
    }

    public void updateHitbox(int x, int y) {
        rectangle.setLocation((x >> 5) - offset.getX(), (y >> 5) - offset.getY());
    }

    public void setHitboxDimension(int width, int height) {
        rectangle.setSize(width, height);
    }

    public Rectangle getHitboxRectangle() {
        return rectangle;
    }


    public void setOffset(int x, int y) {
        offset.set(x, y);
    }
}
