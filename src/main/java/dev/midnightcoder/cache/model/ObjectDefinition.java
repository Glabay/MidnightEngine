package dev.midnightcoder.cache.model;

import java.io.Serializable;

public class ObjectDefinition implements Serializable {
    private final int id;
    private final String[] actions = {
        "",
        "",
        "",
        "",
        "Examine"
    };

    private int textureId;

    private String name;
    private String description;

    public ObjectDefinition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public String[] getActions() {
        return actions;
    }
}
