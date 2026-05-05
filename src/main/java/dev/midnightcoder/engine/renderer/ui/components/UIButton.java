package dev.midnightcoder.engine.renderer.ui.components;

import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class UIButton extends UIComponent {
    private final AWTMouseInputHandler mouseInputHandler;
    private final UIAction action;

    private UIButtonListener buttonListener;
    private UILabel label;

    private boolean inside = false;
    private boolean pressed = false;
    private boolean ignorePressed = false;

    private Image icon;

    public UIButton(AWTMouseInputHandler mouseHandler, Vec2i position, Vec2i size, UIAction actionListnener) {
        super(position, size);
        this.mouseInputHandler = mouseHandler;
        this.action = actionListnener;
        Vec2i lp = new Vec2i(position);
        lp.x += 6;
        lp.y += size.y - 6;
        label = new UILabel(lp, "");
        label.dropShadow = false;
        label.active = false;
        init();
    }

    public UIButton(AWTMouseInputHandler mouseHandler, Vec2i position, Vec2i size, String text, UIAction actionListnener) {
        super(position, size);
        this.mouseInputHandler = mouseHandler;
        this.action = actionListnener;
        Vec2i lp = new Vec2i(position);
        lp.x += 6;
        lp.y += size.y - 6;
        label = new UILabel(lp, text);
        label.dropShadow = false;
        label.active = false;
        init();
    }

    public UIButton(AWTMouseInputHandler mouseHandler, Vec2i position, Vec2i size, Vec2i offset, String text, Color color, UIAction actionListnener) {
        super(position, size);
        this.mouseInputHandler = mouseHandler;
        this.action = actionListnener;
        Vec2i lp = new Vec2i(position);
        lp.x += offset.x;
        lp.y += offset.y;
        label = new UILabel(lp, text, color);
        label.dropShadow = false;
        label.active = false;
        init();
    }

    public UIButton(AWTMouseInputHandler mouseHandler, Vec2i position, BufferedImage icon, UIAction actionListnener) {
        super(position, new Vec2i(icon.getWidth(), icon.getHeight()));
        this.mouseInputHandler = mouseHandler;
        this.action = actionListnener;
        setColor(0xCDCDCD);
        setImage(icon);
        init();
    }

    private void init() {
        buttonListener = new UIButtonListener();
    }

    protected void init(UIPanel panel) {
        super.init(panel);
        if (label != null) panel.addComponent(label);
    }

    public void setImage(Image icon) {
        this.icon = icon;
    }

    public void setTextColor(int color) {
        assert (label != null);
        label.setColor(color);
    }

    public void setText(String text) {
        if (text.isBlank()) label.active = false;
        else label.text = text;
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
        label.position.set(x, y + 28);
    }

    public void update() {
        Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
        boolean leftMouseButtonDown = mouseInputHandler.getButton() == MouseEvent.BUTTON1;
        if (rect.contains(new Point(mouseInputHandler.getX(), mouseInputHandler.getY()))) {
            if (!inside) {
                ignorePressed = leftMouseButtonDown;

                buttonListener.entered(this);
            }
            inside = true;

            if (!pressed && !ignorePressed && leftMouseButtonDown) {
                buttonListener.pressed(this);
                pressed = true;
            }
            else if (mouseInputHandler.getButton() == MouseEvent.NOBUTTON) {
                if (pressed) {
                    buttonListener.released(this);
                    pressed = false;
                    action.execute();
                }
                ignorePressed = false;
            }
        }
        else {
            if (inside) {
                buttonListener.exited(this);
                pressed = false;
            }
            inside = false;
        }
    }

    public void render(Renderer renderer) {
        int x = position.x + offset.x;
        int y = position.y + offset.y;

        if (icon != null) {
            renderer.renderImage(icon, x, y);
        } else {
            renderer.setColor(color);
            renderer.getGraphics2D().fillRect(x, y, size.getWidth(), size.getHeight());

            if (label != null)
                label.render(renderer);
        }
    }
}
