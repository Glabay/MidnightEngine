package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.PlayerMovement;
import dev.midnightcoder.engine.world.GameMap;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class PlayerAvatar extends Mob {
    private final InputManager input;
    private final PlayerMovement movement;

    private final GameMap currentMap;

    public PlayerAvatar(GameMap currentMap, InputManager input, PlayerMovement movement) {
        super(100, 100);
        this.input = input;
        this.movement = movement;
        this.currentMap = currentMap;
        width = 32;
        height = 32;

        texture = TextureFactory.createSolidColor(32, 32, Color.RED);
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

        var dx = (int) (getMoveX() * speed * delta);
        var dy = (int) (getMoveY() * speed * delta);

        movement.move(this, dx, dy);
        currentMap.getCamera().follow(getX() + (getWidth() / 2f), getY() + (getHeight() / 2f));
    }

    @Override
    public void render(Renderer renderer) {
        if (texture != null) {
            var screenX = (int) (getX() - currentMap.getCamera().getX());
            var screenY = (int) (getY() - currentMap.getCamera().getY());
            renderer.renderTexture(texture, screenX, screenY);
        }
    }
}
