package dev.midnightcoder.engine.world.loader;

import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.TileMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public abstract class MapLoader {
    public abstract TileMap loadMapFile(String path);
    public abstract void loadObjectMapFile(GameMap currentMap, String path);
}
