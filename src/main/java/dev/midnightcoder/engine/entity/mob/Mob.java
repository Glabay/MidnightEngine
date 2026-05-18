package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.entity.Hitbox;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.world.GameMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Mob extends Entity {
    protected final int moveSpeed = 1;
    protected final Movement movement;
    protected final GameMap currentMap;

    protected int x;
    protected int y;
    protected int speed = 3;

    protected Direction direction = Direction.SOUTH;

    public Mob(int x, int y, int width, int height, Movement movement, GameMap currentMap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.movement = movement;
        this.currentMap = currentMap;
        this.hitbox = new Hitbox(x, y, width, height);
    }

    @Override
    public void render(Renderer renderer) {
        if (texture != null) {
            var screenX = (int) (x - currentMap.getCamera().getX());
            var screenY = (int) (y - currentMap.getCamera().getY());
            renderer.renderTexture(texture, screenX, screenY);
        }
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
