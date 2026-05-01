package dev.midnightcoder.engine.renderer.java2d;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.Texture;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class Java2DRenderer implements Renderer {
    private final Canvas canvas;

    private final BufferStrategy bufferStrategy;
    private Graphics2D gfx2D;

    public Java2DRenderer(Canvas canvas) {
        this.canvas = canvas;
        this.bufferStrategy = canvas.getBufferStrategy();
    }

    @Override
    public void begin() {
        gfx2D = (Graphics2D) bufferStrategy.getDrawGraphics();

        gfx2D.setColor(Color.BLACK);
        gfx2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gfx2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    @Override
    public void end() {
        gfx2D.dispose();
        bufferStrategy.show();

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void update() {}

    @Override
    public void renderTexture(Texture texture, int x, int y) {
        getGraphics2D().drawImage(texture.image(), x, y, null);
    }

    public Graphics2D getGraphics2D() {
        return gfx2D;
    }
}
