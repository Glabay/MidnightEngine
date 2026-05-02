package dev.midnightcoder.engine.renderer.ui;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.renderer.ui.interfaces.TopHUD;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class UIManager {
    private TopHUD topHud;

    private final List<UIPanel> panels = new ArrayList<>();

    public void addPanel(UIPanel panel) {
        panels.add(panel);
    }

    public void update() {
        for (var panel : panels) {
            panel.update();
        }
    }

    public void render(Renderer renderer) {
        for (var panel : panels) {
            panel.render(renderer);
        }
    }
}
