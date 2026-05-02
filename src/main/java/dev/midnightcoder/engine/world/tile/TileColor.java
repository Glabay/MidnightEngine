package dev.midnightcoder.engine.world.tile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public enum TileColor {
    GRASS_PLAIN("0xFF00FF00"),
    STONE_WALL("0xFF7F7F7F"),
    STONE_WALL_TOP("0xFF6F6F6F"),
    WOOD_WALL("0xFF774500"),
    WOOD_WALL_TOP("0xFF603800"),
    WOOD_FLOOR("0xFF7F7F00"),
    STONE_FLOOR("0xFF7B8C7E"),
    GRASS("0xFF00FF00"),
    GARDEN_PATCH("0xFF2A882A"),
    WATER_TILE("0xFF0000FF"),
    STONE_PATH("0xFFA0A0A0")
    ;

    private final String colorCode;

    TileColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
