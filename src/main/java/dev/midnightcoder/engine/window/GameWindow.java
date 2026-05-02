package dev.midnightcoder.engine.window;

import dev.midnightcoder.engine.input.AWTInputManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class GameWindow extends Canvas {

    private final JFrame frame;
    private final AWTInputManager inputManager;

    public GameWindow(String title, int width, int height, AWTInputManager inputManager) {
        this.inputManager = inputManager;

        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true);
        setFocusable(true);
        requestFocus();

        frame = new JFrame(title);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        createBufferStrategy(3);

        addKeyListener(inputManager);
    }

    public Canvas getCanvas() {
        return this;
    }
}
