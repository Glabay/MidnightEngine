package dev.midnightcoder.engine.entity.mob;

import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;

import java.awt.image.BufferedImage;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public abstract class Avatar extends Mob {
    public Avatar(Vec2i position, Movement movement, GameMap currentMap) {
        super(position.getX(), position.getY(), movement, currentMap);
    }

    public abstract int getMoveX();
    public abstract int getMoveY();

    public void setAvatarTexture(BufferedImage texture) {
        this.texture.setImage(texture);
    }
}
