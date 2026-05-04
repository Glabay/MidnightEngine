package dev.midnightcoder.engine.input.mouse;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public interface MouseInputManager {
    int getX();
    int getY();

    boolean isButtonPressed(int button);

    void update();
}
