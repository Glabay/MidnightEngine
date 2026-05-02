package dev.midnightcoder.engine.world;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.camera.Camera2D;
import dev.midnightcoder.engine.world.tile.Tile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public abstract class GameMap {
    protected TileMap tileMap;
    protected Camera2D camera;

    protected int mapWidth;
    protected int mapHeight;

    protected final int SPRITE_SIZE = 32;

    public void renderTileMap(Renderer renderer) {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Tile tile = tileMap.getTile(x, y);
                if (tile != null) {
                    var worldX = x * Tile.TILE_SIZE;
                    var worldY = y * Tile.TILE_SIZE;

                    var screenX = (int) (worldX - camera.getX());
                    var screenY = (int) (worldY - camera.getY());
                    renderer.renderTexture(
                        tile.type().getTexture(),
                        screenX,
                        screenY
                    );
                }
            }
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public int getSpriteSize() {
        return SPRITE_SIZE;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

}
