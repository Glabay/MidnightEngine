package dev.midnightcoder.engine.world.tile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public record Tile(
    int x,
    int y,
    TileType type
) {
    public static final int TILE_SIZE = 32;
}
