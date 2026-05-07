package dev.midnightcoder.engine.system;

import dev.midnightcoder.engine.entity.mob.Mob;
import dev.midnightcoder.engine.world.TileMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public abstract class Movement {
    protected final TileMap tileMap;

    public Movement(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public abstract void move(Mob mob, int dx, int dy);

}
