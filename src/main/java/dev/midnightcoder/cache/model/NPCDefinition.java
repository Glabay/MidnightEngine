package dev.midnightcoder.cache.model;

import java.io.Serializable;

public class NPCDefinition implements Serializable {
    private final int id;

    private final String[] actions = {
        "",
        "",
        "",
        "",
        "Examine"
    };

    private int spriteSheetId;
    private int size;
    private int combatLevel;

    private String name;
    private String description;

    public NPCDefinition(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSpriteSheetId() {
        return spriteSheetId;
    }

    public void setSpriteSheetId(int spriteSheetId) {
        this.spriteSheetId = spriteSheetId;
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

    public int getCombatLevel() {
        return combatLevel;
    }

    public void setCombatLevel(int combatLevel) {
        this.combatLevel = combatLevel;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String[] getActions() {
        return actions;
    }
}
