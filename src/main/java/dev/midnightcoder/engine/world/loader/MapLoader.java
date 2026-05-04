package dev.midnightcoder.engine.world.loader;

import dev.midnightcoder.engine.world.TileMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public abstract class MapLoader {
    public abstract TileMap loadMapFile(String path);
}
