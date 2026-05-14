package dev.midnightcoder.cache.model;

import java.io.Serializable;

public class Sprite implements Serializable {
    private final int id;

    private byte[] pngData;
    private int width;
    private int height;

    public Sprite(int id, byte[] pngData, int width, int height) {
        this.id = id;
        this.pngData = pngData;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public byte[] getPngData() {
        return pngData;
    }

    public void setPngData(byte[] pngData) {
        this.pngData = pngData;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getMemoryUsage() {
        return (long) width * height * 4;
    }
}
