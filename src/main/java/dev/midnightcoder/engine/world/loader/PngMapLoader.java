package dev.midnightcoder.engine.world.loader;

import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.engine.world.tile.TileColorRegistry;

import javax.imageio.ImageIO;
import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class PngMapLoader {
    private final TileColorRegistry registry;

    public PngMapLoader(TileColorRegistry tileColorRegistry) {
        this.registry = tileColorRegistry;
    }

    public TileMap loadMapFile(String path) {
        try {
            var file = getClass().getResourceAsStream(path);
            if (file == null)
                throw new RuntimeException("Map file not found: " + path);

            var image = ImageIO.read(file);

            var width = image.getWidth();
            var height = image.getHeight();

            var tileMap = new TileMap(width, height);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    var color = new Color(image.getRGB(x, y), true);
                    var tileType = registry.getTileType(color);
                    if (tileType == null)
                        continue;

                    tileMap.setTile(x, y, new Tile(x, y, tileType));
                }
            }

            return tileMap;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load map file: " + path, e);
        }
    }
}
