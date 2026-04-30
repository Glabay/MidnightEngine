package dev.midnightcoder.engine.renderer.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

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
}
