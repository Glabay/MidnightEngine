package dev.midnightcoder.engine.input.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class AWTMouseInputHandler implements MouseInputManager, MouseMotionListener, MouseListener {
    protected volatile int mouseX = -1;
    protected volatile int mouseY = -1;
    protected volatile int mouseB = MouseEvent.NOBUTTON;

    public int getButton() {
        return mouseB;
    }

    public int getX() {
        return mouseX;
    }
    public int getY() {
        return mouseY;
    }

    @Override
    public boolean isButtonPressed(int button) {
        return mouseB == button;
    }

    @Override
    public void update() {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = MouseEvent.NOBUTTON;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
