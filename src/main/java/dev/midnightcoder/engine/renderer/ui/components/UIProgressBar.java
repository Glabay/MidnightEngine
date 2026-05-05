package dev.midnightcoder.engine.renderer.ui.components;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public class UIProgressBar extends UIComponent {
    private final Vec2i size;

    private double progress;

    private Color forgroundColor;

    public UIProgressBar(Vec2i position, Vec2i size) {
        super(position, size);
        this.size = size;

        forgroundColor = new Color(0xff00ff);
    }

    public void setForgroundColor(Color forgroundColor) {
        this.forgroundColor = forgroundColor;
    }

    public void setProgress(double progress) {
        if (progress <= 0.0) progress = 0.0;
        if (progress >= 1.0) progress = 1.0;

        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }

    public void render(Renderer renderer) {
        renderer.setColor(color);
        renderer.getGraphics2D().fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);

        renderer.setColor(forgroundColor);
        renderer.getGraphics2D().fillRect(position.x + offset.x, position.y + offset.y, (int) (progress * size.x), size.y);

    }
}
