package dev.midnightcoder.cache.model;

import dev.midnightcoder.engine.entity.Direction;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

public class NPCDefinition implements Serializable {
    private final int id;
    protected final Map<Direction, BufferedImage[]> animatedFrames = new EnumMap<>(Direction.class);

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

    // Combat
    private int attackSpeed = 4;
    private int health = 10;
    private int attack = 1;
    private int strength = 1;
    private int defence = 1;
    private int ranged = 1;
    private int magic = 1;
    private int deathDelay = 4;

    public NPCDefinition(int id) {
        this.id = id;
    }

    public void setAnimatedFrames(Direction direction, BufferedImage[] frames) {
        animatedFrames.put(direction, frames);
    }

    public BufferedImage[] getAnimatedFrames(Direction direction) {
        return animatedFrames.get(direction);
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

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getAtkSpeed() {
        return attackSpeed;
    }

    public void setAtkSpeed(int atkSpeed) {
        this.attackSpeed = atkSpeed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHitpoints() {
        return health;
    }

    public void setHitpoints(int hitpoints) {
        this.health = hitpoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttackLevel() {
        return attack;
    }

    public void setAttackLevel(int attackLevel) {
        this.attack = attackLevel;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrengthLevel() {
        return strength;
    }

    public void setStrengthLevel(int strengthLevel) {
        this.strength = strengthLevel;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getDefenceLevel() {
        return defence;
    }

    public void setDefenceLevel(int defenceLevel) {
        this.defence = defenceLevel;
    }

    public int getRanged() {
        return ranged;
    }

    public void setRanged(int ranged) {
        this.ranged = ranged;
    }

    public int getRangedLevel() {
        return ranged;
    }

    public void setRangedLevel(int rangedLevel) {
        this.ranged = rangedLevel;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getMagicLevel() {
        return magic;
    }

    public void setMagicLevel(int magicLevel) {
        this.magic = magicLevel;
    }

    public int getDeathDelay() {
        return deathDelay;
    }

    public void setDeathDelay(int deathDelay) {
        this.deathDelay = deathDelay;
    }
}
