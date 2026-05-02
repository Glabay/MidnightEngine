package dev.midnightcoder.engine.world.tile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public enum TileColor {
    GRASS_PLAIN("0xFF00FF00")

    ;

    private final String colorCode;

    TileColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
