package dev.midnightcoder.engine.world.tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class TileColorRegistry {
    private final Map<String, TileType> tileTypeRegistry = new HashMap<>();

    public void register(TileColor color, TileType tileType) {
        tileTypeRegistry.put(color.getColorCode(), tileType);
    }

    public TileType getTileType(String colorCode) {
        return tileTypeRegistry.get(colorCode);
    }

    public int getRegistrySize() {
        return tileTypeRegistry.size();
    }
}
