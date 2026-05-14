package dev.midnightcoder.cache;

import dev.midnightcoder.cache.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CacheManager {
    private static final Logger log = LoggerFactory.getLogger(CacheManager.class);
    private final List<Sprite> sprites = new ArrayList<>();
    private final List<SpriteSheet> spriteSheets = new ArrayList<>();
    private final List<Texture> textures = new ArrayList<>();
    private final List<ItemDefinition> items = new ArrayList<>();
    private final List<NPCDefinition> npcs = new ArrayList<>();
    private final List<ObjectDefinition> objects = new ArrayList<>();
    private final List<MapDefinition> maps = new ArrayList<>();

    private static final String CACHE_DIR = "midnight_cache";

    public CacheManager() {
        var dir = new File(CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void load() {
        try {
            sprites.clear();
            sprites.addAll(loadIndex("sprites.dat"));
            spriteSheets.clear();
            spriteSheets.addAll(loadIndex("spritesheets.dat"));
            textures.clear();
            textures.addAll(loadIndex("textures.dat"));
            items.clear();
            items.addAll(loadIndex("items.dat"));
            npcs.clear();
            npcs.addAll(loadIndex("npcs.dat"));
            objects.clear();
            objects.addAll(loadIndex("objects.dat"));
            maps.clear();
            maps.addAll(loadIndex("maps.dat"));
        }
        catch (Exception e) {
            log.error("Error loading cache: {}", e.getMessage());
        }
    }

    public void save() {
        try {
            saveIndex("sprites.dat", sprites);
            saveIndex("spritesheets.dat", spriteSheets);
            saveIndex("textures.dat", textures);
            saveIndex("items.dat", items);
            saveIndex("npcs.dat", npcs);
            saveIndex("objects.dat", objects);
            saveIndex("maps.dat", maps);
        }
        catch (Exception e) {
            log.error("Error saving cache: {}", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> loadIndex(String filename) throws Exception {
        var file = new File(CACHE_DIR, filename);
        if (!file.exists()) return new ArrayList<>();
        try (var ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)))) {
            return (List<T>) ois.readObject();
        }
    }

    private <T> void saveIndex(String filename, List<T> data) throws Exception {
        var file = new File(CACHE_DIR, filename);
        try (var oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(data);
        }
    }

    // Sprites
    public List<Sprite> getSprites() {
        return sprites;
    }

    public void addSprite(byte[] pngData, int w, int h) {
        sprites.add(new Sprite(sprites.size(), pngData, w, h));
    }

    public void replaceSprite(int id, byte[] pngData, int w, int h) {
        if (id >= 0 && id < sprites.size()) {
            sprites.set(id, new Sprite(id, pngData, w, h));
        }
    }

    // SpriteSheets
    public List<SpriteSheet> getSpriteSheets() {
        return spriteSheets;
    }

    public void addSpriteSheet(int spriteId, int rows, int cols, int fw, int fh) {
        spriteSheets.add(new SpriteSheet(spriteSheets.size(), spriteId, rows, cols, fw, fh));
    }

    public void replaceSpriteSheet(int id, int spriteId, int rows, int cols, int fw, int fh) {
        if (id >= 0 && id < spriteSheets.size()) {
            spriteSheets.set(id, new SpriteSheet(id, spriteId, rows, cols, fw, fh));
        }
    }

    // Textures
    public List<Texture> getTextures() {
        return textures;
    }

    public void addTexture(int spriteId, String colorHex) {
        textures.add(new Texture(textures.size(), spriteId, colorHex));
    }

    public void replaceTexture(int id, int spriteId, String colorHex) {
        if (id >= 0 && id < textures.size()) {
            textures.set(id, new Texture(id, spriteId, colorHex));
        }
    }

    // Items
    public List<ItemDefinition> getItems() {
        return items;
    }

    public void addItem() {
        items.add(new ItemDefinition(items.size()));
    }

    public void replaceItem(int id, ItemDefinition def) {
        if (id >= 0 && id < items.size()) {
            items.set(id, def);
        }
    }

    // NPCs
    public List<NPCDefinition> getNpcs() {
        return npcs;
    }

    public void addNpc() {
        npcs.add(new NPCDefinition(npcs.size()));
    }

    public void replaceNpc(int id, NPCDefinition def) {
        if (id >= 0 && id < npcs.size()) {
            npcs.set(id, def);
        }
    }

    // Objects
    public List<ObjectDefinition> getObjects() {
        return objects;
    }

    public void addObject() {
        objects.add(new ObjectDefinition(objects.size()));
    }

    public void replaceObject(int id, ObjectDefinition def) {
        if (id >= 0 && id < objects.size()) {
            objects.set(id, def);
        }
    }

    // Maps
    public List<MapDefinition> getMaps() {
        return maps;
    }

    public MapDefinition getMap(UUID id) {
        return maps.stream()
            .filter(map -> map.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Map definition not found for ID: " + id));
    }

    public void addMap(byte[] pngData) {
        maps.add(new MapDefinition(UUID.randomUUID(), pngData));
    }

    public void replaceMap(UUID id, byte[] pngData) {
        for (int i = 0; i < maps.size(); i++) {
            if (maps.get(i).getId().equals(id)) {
                maps.set(i, new MapDefinition(id, pngData));
                return;
            }
        }
    }
}
