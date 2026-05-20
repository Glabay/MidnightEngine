package dev.midnightcoder.engine.window;

import dev.midnightcoder.engine.input.keyboard.AWTKeyboardInputManager;
import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;

import javax.swing.*;
import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class GameWindow extends Canvas {

    public GameWindow(String title, int width, int height, AWTKeyboardInputManager inputManager, AWTMouseInputHandler mouseManager) {
        setPreferredSize(new Dimension(width, height));
        setIgnoreRepaint(true);
        setFocusable(true);
        requestFocus();

        var frame = new JFrame(title);
            frame.setResizable(false);
            frame.add(this);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        createBufferStrategy(3);

        addKeyListener(inputManager);
        addMouseListener(mouseManager);
        addMouseMotionListener(mouseManager);
        addMouseWheelListener(mouseManager);
    }

    public Canvas getCanvas() {
        return this;
    }
}
