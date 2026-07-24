package dev.midnightcoder.engine.util;

import java.util.Random;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-06-29
 */
public record Boundary(
    Vec2i min,
    Vec2i max
) {
    public Vec2i getRandomPosition() {
        var random = new Random();
        return new Vec2i(
            random.nextInt(min.getX(), max.getX()),
            random.nextInt(min.getY(), max.getY())
        );
    }
}
