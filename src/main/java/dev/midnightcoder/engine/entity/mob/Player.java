package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.renderer.graphics.Texture;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class Player extends Entity {


    public Player(int x, int y, Texture texture) {
        super(x, y, texture);
    }

    @Override
    public void update(double delta) {

    }
}
