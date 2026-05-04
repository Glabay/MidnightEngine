package dev.midnightcoder.engine.input.keyboard;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public interface KeyboardInputManager {
    boolean isKeyPressed(int keyCode);
    boolean isKeyReleased(int keyCode);
    boolean isKeyHeld(int keyCode);
    boolean noKeysHeld();

    void update();
}
