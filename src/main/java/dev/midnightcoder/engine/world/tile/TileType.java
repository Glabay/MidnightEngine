package dev.midnightcoder.engine.world.tile;

import dev.midnightcoder.engine.renderer.graphics.Texture;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public abstract class TileType {
    private final String id;
    private final Texture texture;
    private final int collisionFlags;

    public TileType(String id, Texture texture, int collisionFlags) {
        this.id = id;
        this.texture = texture;
        this.collisionFlags = collisionFlags;
    }

    public abstract Rectangle getHitbox(int x, int y);

    public String getId() {
        return id;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getCollisionFlags() {
        return collisionFlags;
    }

    public boolean isBlocked(CollisionFlag flag) {
        return CollisionFlag.has(collisionFlags, flag);
    }
}
