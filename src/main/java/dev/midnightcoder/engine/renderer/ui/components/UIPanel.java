package dev.midnightcoder.engine.renderer.ui.components;

import dev.midnightcoder.engine.entity.item.GameItem;
import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class UIPanel extends UIComponent {
    protected List<UIComponent> children = new ArrayList<>();

    protected Vec2i size;

    protected BufferedImage background;

    protected GameItem item;

    protected boolean visible;
    protected boolean mousePressed;

    public UIPanel(Vec2i position, Vec2i size) {
        super(position);
        this.position = position;
        this.size = size;
    }

    public void addComponent(UIComponent component) {
        component.init(this);
        children.add(component);
    }

    public void update() {
        if (visible) {
            for (var components : children) {
                components.setOffset(position);
                components.update();
            }
        }
    }

    public void render(Renderer renderer) {
        if (visible) {
            renderer.setColor(color);
            renderer.getGraphics2D().fillRect(position.x, position.y, size.x, size.y);
            for (UIComponent components : children) {
                components.render(renderer);
            }
        }
    }

    public UIPanel display() {
        visible = !visible;
        return this;
    }

    public void setSelectedItem(GameItem item) {
        this.item = item;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public GameItem getSelectedGameItem() {
        return item;
    }
}
