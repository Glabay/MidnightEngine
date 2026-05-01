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
    private final Map<Integer, TileType> tileTypeRegistry = new HashMap<>();

    public void register(Color color, TileType tileType) {
        tileTypeRegistry.put(color.getRGB(), tileType);
    }

    public TileType getTileType(Color color) {
        return tileTypeRegistry.get(color.getRGB());
    }
}
