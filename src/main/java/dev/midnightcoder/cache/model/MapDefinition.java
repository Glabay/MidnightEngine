package dev.midnightcoder.cache.model;

import java.io.Serializable;
import java.util.UUID;

public class MapDefinition implements Serializable {
    private final UUID id;
    private String fileName;

    private byte[] pngData;

    public MapDefinition(UUID id, byte[] pngData) {
        this.id = id;
        this.pngData = pngData;
    }

    public UUID getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getPngData() {
        return pngData;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPngData(byte[] pngData) {
        this.pngData = pngData;
    }
}
