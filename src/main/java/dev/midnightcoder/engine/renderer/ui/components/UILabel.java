package dev.midnightcoder.engine.renderer.ui.components;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class UILabel extends UIComponent {
    public boolean dropShadow = true;

    public String text;
    private Font font;

    public UILabel(Vec2i position, String text) {
        super(position);
        this.text = text;
    }

    public UILabel(Vec2i position, String text, Color color) {
        super(position);
        this.text = text;
        this.color = color;
    }

    public UILabel setFont(Font font) {
        this.font = font;
        return this;
    }

    public void setTextColor(Color color) {
        this.color = color;
    }

    public void updateText(String text) {
        this.text = text;
    }

    public void render(Renderer renderer) {
        if (dropShadow) {
            renderer.setFont(font);
            renderer.setColor(Color.BLACK);
            int dropShadowOffset = 2;
            renderer.renderText(text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
        }
        renderer.setFont(font);
        renderer.setColor(color);
        renderer.renderText((text != null) ? text : "", position.x + offset.x, position.y + offset.y);
    }
}
