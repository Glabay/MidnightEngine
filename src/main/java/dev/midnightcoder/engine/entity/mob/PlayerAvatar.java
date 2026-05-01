package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.system.Movement;

import java.awt.event.KeyEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class PlayerAvatar extends Entity {

    private final InputManager input;
    private final Movement movement;

    private final int moveSpeed = 1;
    private int speed = 3;

    public PlayerAvatar(int x, int y, Texture texture, InputManager input, Movement movement) {
        super(x, y, texture);
        this.input = input;
        this.movement = movement;
        width = 32;
        height = 32;
    }

    public int getMoveX() {
        var dx = 0;
        if (input.isKeyHeld(KeyEvent.VK_A)) dx -= moveSpeed;
        if (input.isKeyHeld(KeyEvent.VK_D)) dx += moveSpeed;
        return dx;
    }

    public int getMoveY() {
        var dy = 0;
        if (input.isKeyHeld(KeyEvent.VK_W)) dy -= moveSpeed;
        if (input.isKeyHeld(KeyEvent.VK_S)) dy += moveSpeed;
        return dy;
    }

    @Override
    public void update(double delta) {
        // running
        if (input.isKeyHeld(KeyEvent.VK_SHIFT))
            speed = 4;
        else
            speed = 3;

        var dx = getMoveX() * speed * (int) delta;
        var dy = getMoveY() * speed * (int) delta;

        movement.move(this, dx, dy);
    }
}
