package dev.midnightcoder.cache.model;

import java.io.Serializable;

public class ItemDefinition implements Serializable {
    private final int id;
    private final String[] backpackActions = {
        "",
        "Use",
        "",
        "Drop",
        "Examine"
    };
    private final String[] groundActions = {
        "Pick-up",
        "",
        "",
        "",
        "Examine"
    };

    private int spriteId;

    private String name;
    private String description;

    // Bonuses
    private int offAccuracy;
    private int offMelee;
    private int offRanged;
    private int offMagic;

    private int defMelee;
    private int defRanged;
    private int defMagic;
    private int atkSpeed = 4;

    private int value;
    private boolean tradeable = true;

    public ItemDefinition(int id) {
        this.id = id;
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

    public String[] getBackpackActions() {
        return backpackActions;
    }

    public String[] getGroundActions() {
        return groundActions;
    }

    public int getOffAccuracy() {
        return offAccuracy;
    }

    public void setOffAccuracy(int offAccuracy) {
        this.offAccuracy = offAccuracy;
    }

    public int getOffMelee() {
        return offMelee;
    }

    public void setOffMelee(int offMelee) {
        this.offMelee = offMelee;
    }

    public int getOffRanged() {
        return offRanged;
    }

    public void setOffRanged(int offRanged) {
        this.offRanged = offRanged;
    }

    public int getOffMagic() {
        return offMagic;
    }

    public void setOffMagic(int offMagic) {
        this.offMagic = offMagic;
    }

    public int getDefMelee() {
        return defMelee;
    }

    public void setDefMelee(int defMelee) {
        this.defMelee = defMelee;
    }

    public int getDefRanged() {
        return defRanged;
    }

    public void setDefRanged(int defRanged) {
        this.defRanged = defRanged;
    }

    public int getDefMagic() {
        return defMagic;
    }

    public void setDefMagic(int defMagic) {
        this.defMagic = defMagic;
    }

    public int getAtkSpeed() {
        return atkSpeed;
    }

    public void setAtkSpeed(int atkSpeed) {
        this.atkSpeed = atkSpeed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isTradeable() {
        return tradeable;
    }

    public void setTradeable(boolean tradeable) {
        this.tradeable = tradeable;
    }
}
