package dev.midnightcoder.engine.window;

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

    public GameWindow(String title, int width, int height) {
        frame = new JFrame(title);
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setIgnoreRepaint(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        canvas.createBufferStrategy(3);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
