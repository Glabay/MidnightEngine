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
public class GameWindow {

    private final JFrame frame;
    private final Canvas canvas;
    private final AWTInputManager inputManager;

    public GameWindow(String title, int width, int height, AWTInputManager inputManager) {
        frame = new JFrame(title);
        this.inputManager = inputManager;
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setIgnoreRepaint(true);
        canvas.setFocusable(true);
        canvas.requestFocus();

        canvas.addKeyListener(inputManager);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas.createBufferStrategy(3);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
