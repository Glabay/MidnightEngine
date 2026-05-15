package dev.midnightcoder.cache.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;

public class AudioDefinition implements Serializable {
    private int id;
    private String name;
    private byte[] data;
    private long compressedSize;
    private double duration;

    public AudioDefinition(int id) {
        this.id = id;
    }

    public AudioDefinition(int id, String name, byte[] data, long compressedSize, double duration) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.compressedSize = compressedSize;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public void setCompressedSize(long compressedSize) {
        this.compressedSize = compressedSize;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public byte[] getDecompressedData() {
        if (data == null || data.length == 0) return new byte[0];
        try (var bais = new ByteArrayInputStream(data);
             var gzis = new GZIPInputStream(bais)) {
            return gzis.readAllBytes();
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to decompress audio data", e);
        }
    }
}
