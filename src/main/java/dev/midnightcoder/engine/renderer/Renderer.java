package dev.midnightcoder.engine.renderer;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.util.Vec2i;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public interface Renderer {
    void begin();
    void end();
    void update();

    void renderTexture(Texture texture, int x, int y);
    void renderImage(Image image, int x, int y);
    void renderImage(Image image, int x, int y, int width, int height);

    void renderText(String text, int x, int y);
    void setFont(Font font);
    void setColor(Color color);

    Graphics2D getGraphics2D();

    void drawRectangle(Vec2i position, int size, int size1, Color white);

    default void fillCircle(int centerX, int centerY, int radius, Color color) {
        if (getGraphics2D() != null) {
            getGraphics2D().setColor(color);
            getGraphics2D().fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }

    default void drawCircle(int centerX, int centerY, int radius, Color color) {
        if (getGraphics2D() != null) {
            getGraphics2D().setColor(color);
            getGraphics2D().drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }
}
