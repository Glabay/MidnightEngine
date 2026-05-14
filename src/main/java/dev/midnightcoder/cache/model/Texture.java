package dev.midnightcoder.cache.model;

import java.io.Serializable;

public class Texture implements Serializable {
    private final int id;

    private int spriteId = -1;
    private int spriteSheetId = -1;
    private int frameIndex = -1;
    private String colorHex;

    public Texture(int id, int spriteId, String colorHex) {
        this.id = id;
        this.spriteId = spriteId;
        this.colorHex = colorHex;
    }

    public int getId() {
        return id;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
        this.spriteSheetId = -1;
        this.frameIndex = -1;
    }

    public int getSpriteSheetId() {
        return spriteSheetId;
    }

    public void setSpriteSheetId(int spriteSheetId) {
        this.spriteSheetId = spriteSheetId;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
