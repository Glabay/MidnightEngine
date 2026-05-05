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
public class UIComponent {
    public Vec2i position;

    protected Vec2i size;
    protected Vec2i offset;
    protected UIPanel panel;

    public Color color = new Color(0, 0, 0);

    public boolean active = true;

    public UIComponent(Vec2i position, Vec2i size) {
        this.position = position;
        this.size = size;
        offset = new Vec2i();
    }

    public UIComponent(Vec2i position) {
        this.position = position;
        offset = new Vec2i();
    }

    protected void init(UIPanel uiPanel) {
        this.panel = uiPanel;
    }

    public UIComponent setColor(int color) {
        this.color = new Color(color);
        return this;
    }

    public UIComponent setColor(Color color) {
        this.color = color;
        return this;
    }

    public Vec2i getAbsolutePosition() {
        return new Vec2i(position).add(offset);
    }

    public void update() {}

    public void render(Renderer renderer) {}

    public void setOffset(Vec2i offset) {
        this.offset = offset;
    }

}
