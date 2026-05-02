package dev.midnightcoder.engine.system;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.tile.CollisionFlag;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public abstract class Movement {
    private final TileMap tileMap;

    public Movement(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public abstract void move(Entity mob, int dx, int dy);

    protected boolean isBlocked(int tileX, int tileY) {
        var tile = tileMap.getTile(tileX, tileY);
        return tile != null &&
            tile.type().getCollisionFlags() != CollisionFlag.NONE.getMask();
    }
}
