package dev.midnightcoder.engine.input.mouse;

import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class AWTMouseInputHandler implements MouseInputManager, MouseMotionListener, MouseWheelListener, MouseListener {
    protected volatile int mouseX = -1;
    protected volatile int mouseY = -1;
    protected volatile int mouseB = MouseEvent.NOBUTTON;
    protected volatile AtomicInteger wheelRotation = new AtomicInteger(0);

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
    public int getWheelRotation() {
        return wheelRotation.get();
    }

    @Override
    public int consumeWheelRotation() {
        return wheelRotation.getAndSet(0);
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        wheelRotation.set(wheelRotation.get() + e.getWheelRotation());
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
