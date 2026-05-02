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

    /// Color matching
    public static final int	COL_STONE_WALL		= 0xFF7F7F7F;
    public static final int	COL_STONE_WALL_TOP	= 0xFF6F6F6F;
    public static final int	COL_WOOD_WALL		= 0xFF774500;
    public static final int	COL_WOOD_WALL_TOP	= 0xFF603800;

    public static final int	COL_WOOD_FLOOR	    = 0xFF7F7F00;
    public static final int	COL_STONE_FLOOR	    = 0xFF7B8C7E;
    public static final int	COL_GRASS		    = 0xFF00FF00;
    public static final int	COL_DIRT		    = 0;

    public static final int	COL_GARDEN_PATCH	= 0xFF2A882A;
    public static final int	COL_WATER_TILE		= 0xFF0000FF;
    public static final int	COL_STONE_PATH		= 0xFFA0A0A0;

}
