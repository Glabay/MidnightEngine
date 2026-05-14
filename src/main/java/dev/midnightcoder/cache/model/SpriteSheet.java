package dev.midnightcoder.cache.model;

import java.io.Serializable;

public class SpriteSheet implements Serializable {
    private final int id;

    private int spriteId;
    private int rows;
    private int cols;
    private int frameWidth;
    private int frameHeight;

    public SpriteSheet(int id, int spriteId, int rows, int cols, int frameWidth, int frameHeight) {
        this.id = id;
        this.spriteId = spriteId;
        this.rows = rows;
        this.cols = cols;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public int getId() {
        return id;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }
}
