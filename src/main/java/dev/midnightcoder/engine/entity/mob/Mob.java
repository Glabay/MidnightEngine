package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.entity.Entity;
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

    protected int speed = 3;

    public Mob(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(double delta) {
        updateHitbox();
    }

}
