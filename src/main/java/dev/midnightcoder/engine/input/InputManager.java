package dev.midnightcoder.engine.input;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public interface InputManager {
    boolean isKeyPressed(int keyCode);
    boolean isKeyReleased(int keyCode);
    boolean isKeyHeld(int keyCode);

    void update();
}
