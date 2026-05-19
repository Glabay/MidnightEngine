package dev.midnightcoder.cache;

import dev.midnightcoder.cache.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

public class CacheManager {
    private static final Logger log = LoggerFactory.getLogger(CacheManager.class);
    private static final String CACHE_DIR = "midnight_cache";
    private final List<Sprite> sprites = new ArrayList<>();
    private final List<SpriteSheet> spriteSheets = new ArrayList<>();
    private final List<Texture> textures = new ArrayList<>();
    private final List<ItemDefinition> items = new ArrayList<>();
    private final List<NPCDefinition> npcs = new ArrayList<>();
    private final List<ObjectDefinition> objects = new ArrayList<>();
    private final List<MapDefinition> maps = new ArrayList<>();
    private final List<AudioDefinition> audio = new ArrayList<>();
    private final List<DialogueDefinition> dialogues = new ArrayList<>();

    private final DatabaseManager databaseManager;

    public CacheManager() {
        var dir = new File(CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.databaseManager = new DatabaseManager();
    }

    public void load() {
        loadFromSqlite();
        if (isEmpty()) {
            loadLegacy();
            if (!isEmpty()) {
                exportToSqlite();
            }
        }
    }

    private boolean isEmpty() {
        return sprites.isEmpty() && spriteSheets.isEmpty() && textures.isEmpty() &&
                items.isEmpty() && npcs.isEmpty() && objects.isEmpty() && maps.isEmpty() && audio.isEmpty();
    }

    private void loadLegacy() {
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
            audio.clear();
            audio.addAll(loadIndex("audio.dat"));
        }
        catch (Exception e) {
            log.error("Error loading legacy cache: {}", e.getMessage());
        }
    }

    private void loadFromSqlite() {
        try {
            sprites.clear();
            sprites.addAll(databaseManager.loadSprites());
            spriteSheets.clear();
            spriteSheets.addAll(databaseManager.loadSpriteSheets());
            textures.clear();
            textures.addAll(databaseManager.loadTextures());
            items.clear();
            items.addAll(databaseManager.loadItems());
            npcs.clear();
            npcs.addAll(databaseManager.loadNpcs());
            objects.clear();
            objects.addAll(databaseManager.loadObjects());
            maps.clear();
            maps.addAll(databaseManager.loadMaps());
            audio.clear();
            audio.addAll(databaseManager.loadAudio());
            dialogues.clear();
            dialogues.addAll(databaseManager.loadDialogues());
            log.info("Cache loaded from SQLite database.");
        } catch (SQLException e) {
            log.error("Error loading cache from SQLite: {}", e.getMessage());
        }
    }

    public void exportToSqlite() {
        try {
            databaseManager.saveSprites(sprites);
            databaseManager.saveSpriteSheets(spriteSheets);
            databaseManager.saveTextures(textures);
            databaseManager.saveItems(items);
            databaseManager.saveNpcs(npcs);
            databaseManager.saveObjects(objects);
            databaseManager.saveMaps(maps);
            databaseManager.saveAudio(audio);
            databaseManager.saveDialogues(dialogues);
        } catch (SQLException e) {
            log.error("Error exporting cache to SQLite: {}", e.getMessage());
        }
    }

    public void save() {
        exportToSqlite();
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> loadIndex(String filename) throws Exception {
        var file = new File(CACHE_DIR, filename);
        if (!file.exists()) return new ArrayList<>();
        try (var ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)))) {
            return (List<T>) ois.readObject();
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

    // Audio
    public List<AudioDefinition> getAudio() {
        return audio;
    }

    public void addAudio(String name, byte[] data, long compressedSize, double duration) {
        audio.add(new AudioDefinition(audio.size(), name, data, compressedSize, duration));
    }

    public void replaceAudio(int id, AudioDefinition def) {
        if (id >= 0 && id < audio.size()) {
            audio.set(id, def);
        }
    }

    // Dialogues
    public List<DialogueDefinition> getDialogues() {
        return dialogues;
    }

    public void addDialogue() {
        dialogues.add(new DialogueDefinition(dialogues.size()));
    }

    public void replaceDialogue(int id, DialogueDefinition def) {
        if (id >= 0 && id < dialogues.size()) {
            dialogues.set(id, def);
        }
    }
}
