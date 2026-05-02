package dev.midnightcoder.engine.util;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Vec2i {
    public int x, y;

    public Vec2i() {
        set(0, 0);
    }

    public Vec2i(int x, int y) {
        set(x, y);
    }

    public Vec2i(Vec2i vector) {
        set(vector.x, vector.y);
    }

    public Vec2i add(Vec2i vector) {
        this.x += vector.x;
        this.y += vector.y;
        return this;
    }

    public Vec2i addX(int value) {
        this.x += value;
        return this;
    }

    public Vec2i addY(int value) {
        this.y += value;
        return this;
    }

    public static double getDistance(Vec2i v0, Vec2i v1) {
        double x = v0.getX() - v1.getX();
        double y = v0.getY() - v1.getY();

        return Math.sqrt(x * x + y * y);
    }

    public boolean equals(Object object) {
        if (!(object instanceof Vec2i vec)) return false;
        return vec.getX() == this.getX() &&
            vec.getY() == this.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vec2i setY(int y) {
        this.y = y;
        return this;
    }

    public Vec2i subtract(Vec2i vector) {
        this.x -= vector.x;
        this.y -= vector.y;
        return this;
    }

    public String toString() {
        return "(" + x + " " + y + ")";
    }
}
