package dev.midnightcoder.engine.renderer.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class TextureFactory {

    public static Texture createSolidColor(int width, int height, Color color) {
        var texturedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var graphics = texturedImage.createGraphics();
            graphics.setColor(color);
            graphics.fillRect(0, 0, width, height);
            graphics.dispose();

        return new Texture(texturedImage);
    }

    public static Texture createFromImageFile(String path) {
        try {
            var file = TextureFactory.class.getResourceAsStream(path);
            if (file == null)
                throw new IllegalArgumentException("Resource not found: " + path);

            var image = ImageIO.read(file);
            return new Texture(image);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load texture from file: " + path, e);
        }
    }
}
