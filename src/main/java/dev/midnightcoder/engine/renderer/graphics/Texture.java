package dev.midnightcoder.engine.renderer.graphics;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public final class Texture {
    private BufferedImage image;

    public Texture(BufferedImage image) {
        this.image = image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public BufferedImage image() {
        return image;
    }
}
