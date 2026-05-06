package dev.midnightcoder.engine.world.tile;

import java.util.Arrays;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public enum CollisionFlag {
    NONE(0),

    PROGRAMMATIC(1),
    NORTH(1 << 1),
    SOUTH(1 << 2),
    EAST (1 << 3),
    WEST (1 << 4),

    FULL(NORTH.mask | SOUTH.mask | EAST.mask | WEST.mask)
    ;

    private final int mask;

    CollisionFlag(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }

    public static boolean has(int flags, CollisionFlag flag) {
        return (flags & flag.mask) != 0;
    }

    public static int combine(CollisionFlag... flags) {
        return Arrays.stream(flags)
            .mapToInt(f -> f.mask)
            .reduce(0, (a, b) -> a | b);
    }
}
