package dev.midnightcoder.engine.input;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class AWTInputManager implements InputManager, KeyListener, MouseMotionListener, MouseListener {

    private final Set<Integer> currentKeys = new HashSet<>();
    private final Set<Integer> previousKeys = new HashSet<>();

    @Override
    public boolean isKeyPressed(int keyCode) {
        return currentKeys.contains(keyCode) &&
            !previousKeys.contains(keyCode);
    }

    @Override
    public boolean isKeyReleased(int keyCode) {
        return !previousKeys.contains(keyCode) &&
            currentKeys.contains(keyCode);
    }

    @Override
    public boolean isKeyHeld(int keyCode) {
        return currentKeys.contains(keyCode);
    }

    @Override
    public boolean noKeysHeld() {
        return currentKeys.isEmpty();
    }

    @Override
    public void update() {
        previousKeys.clear();
        previousKeys.addAll(currentKeys);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        currentKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKeys.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
