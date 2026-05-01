package dev.midnightcoder.engine.renderer.camera;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class Camera2D {
    private float x;
    private float y;

    private int viewWidth;
    private int viewHeight;

    private int worldWidth;
    private int worldHeight;

    public Camera2D(int viewWidth, int viewHeight, int worldWidth, int worldHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void follow(float targetX, float targetY) {
        x = targetX - viewWidth / 2f;
        y = targetY - viewHeight / 2f;

        clamp();
    }

    private void clamp() {
        if (x < 0) x = 0;
        if (y < 0) y = 0;

        var maxX = worldWidth - viewWidth;
        var maxY = worldHeight - viewHeight;

        if (x > maxX) x = maxX;
        if (y > maxY) y = maxY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
