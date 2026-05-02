package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.Texture;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Mob extends Entity {
    protected final int moveSpeed = 1;

    protected boolean moving = false;
    protected boolean frozen = false;

    protected Direction dir;

    protected int radius = 0;
    protected int speed = 3;

    protected double fireRate = 10.0;

    public int health;
    public int combatLevel;

    protected enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Mob(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
    }

    @Override
    public void update(double delta) {}

    protected void applyDeath() {}

    public void incHealth() {
        health++;
    }

    public void decHealth() {
        health--;
    }

    /**
     *  TODO: if the instance is an instance of an NPC then append the NPC Death
     *  	- This Death method will simply remove the NPC object from the world for a temporary time
     *  	  then respawning to their start location
     */
    public void applyDamage(double dmg) {
    }
}
