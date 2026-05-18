package dev.midnightcoder.engine.entity.mob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class AnimatedSprite {
    private static final Logger log = LoggerFactory.getLogger(AnimatedSprite.class);
    private BufferedImage[] frames;

    private int currentFrameIndex;

    private double frameTimer;
    private double frameDuration;

    private boolean looping;

    public AnimatedSprite(double frameDuration) {
        this.frameDuration = frameDuration;
    }

    public void setFrames(BufferedImage[] frames) {
        if (this.frames == frames)
            return;

        this.frames = frames;
        this.currentFrameIndex = 0;
        this.frameTimer = 0;
    }

    public void update(double delta) {
        if (frames == null || frames.length == 0)
            return;
        frameTimer += delta;

        while (frameTimer >= frameDuration) {
            frameTimer -= frameDuration;
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        }
    }

    public BufferedImage getCurrentFrame() {
        if (frames == null || frames.length == 0)
            return null;
        return frames[currentFrameIndex];
    }

    public void reset() {
        currentFrameIndex = 0;
        frameTimer = 0;
    }
}
