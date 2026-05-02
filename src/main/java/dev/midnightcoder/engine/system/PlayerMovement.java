package dev.midnightcoder.engine.system;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.tile.CollisionFlag;

import static dev.midnightcoder.engine.world.tile.Tile.TILE_SIZE;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class PlayerMovement extends Movement {

    public PlayerMovement(TileMap tileMap) {
        super(tileMap);
    }

    @Override
    public void move(Entity entity, int dx, int dy) {
        if (dx != 0) {
            entity.setX(entity.getX() + dx);
            resolveX(entity, dx);
        }
        if (dy != 0) {
            entity.setY(entity.getY() + dy);
            resolveY(entity, dy);
        }
    }

    private void resolveX(Entity entity, int dx) {
        int top = entity.getY() / TILE_SIZE;
        int bottom =(entity.getY() + entity.getHeight() - 1) / TILE_SIZE;

        if (dx > 0) {
            var right = (entity.getX() + entity.getWidth() - 1) / TILE_SIZE;
            for (int ty = top; ty <= bottom; ty++) {
                // Look left
                if (isBlocked(right, ty)) {
                    entity.setX(right * TILE_SIZE - entity.getWidth());
                    break;
                }
            }
        }
        else if (dx < 0) {
            var left = entity.getX() / TILE_SIZE;
            for (int ty = top; ty <= bottom; ty++) {
                // Look left
                if (isBlocked(left, ty)) {
                    entity.setX((left + 1) * TILE_SIZE);
                    break;
                }
            }
        }
    }

    private void resolveY(Entity entity, int dy) {
        var left = entity.getX() / TILE_SIZE;
        var right = (entity.getX() + entity.getWidth() -1) / TILE_SIZE;

        var top = entity.getY() / TILE_SIZE;
        var bottom = (entity.getY() + entity.getHeight() -1) / TILE_SIZE;

        for (int tx = left; tx <= right; tx++) {
            // look down
            if (dy > 0 && isBlocked(tx, bottom)) {
                entity.setY(top * TILE_SIZE);
                break;
            }
            // look up
            if (dy < 0 && isBlocked(tx, top)) {
                entity.setY(bottom * TILE_SIZE);
                break;
            }
        }
    }

}
