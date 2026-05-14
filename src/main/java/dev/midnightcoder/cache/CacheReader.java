package dev.midnightcoder.cache;

import dev.midnightcoder.cache.model.*;
import dev.midnightcoder.engine.renderer.graphics.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-13
 */
public class CacheReader {
    private static CacheReader instance;

    private final CacheManager cacheManager;
    private final Map<Integer, Texture> textureCache = new HashMap<>();

    public static CacheReader getInstance() {
        if (instance == null) {
            instance = new CacheReader(new CacheManager());
            instance.load();
        }
        return instance;
    }

    public CacheReader(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void load() {
        cacheManager.load();
    }

    public Optional<NPCDefinition> getNPCDefinition(int id) {
        return cacheManager.getNpcs().stream()
                .filter(def -> def.getId() == id)
                .findFirst();
    }

    public Optional<ItemDefinition> getItemDefinition(int id) {
        return cacheManager.getItems().stream()
                .filter(def -> def.getId() == id)
                .findFirst();
    }

    public Optional<ObjectDefinition> getObjectDefinition(int id) {
        return cacheManager.getObjects().stream()
                .filter(def -> def.getId() == id)
                .findFirst();
    }

    public Texture getTexture(int spriteId) {
        if (textureCache.containsKey(spriteId)) {
            return textureCache.get(spriteId);
        }

        return cacheManager.getSprites().stream()
                .filter(sprite -> sprite.getId() == spriteId)
                .findFirst()
                .map(sprite -> {
                    try {
                        if (sprite.getPngData() == null || sprite.getPngData().length == 0) {
                            throw new RuntimeException("Sprite data is empty for ID: " + spriteId);
                        }
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(sprite.getPngData()));
                        if (image == null) {
                            throw new RuntimeException("Failed to decode PNG data for sprite ID: " + spriteId);
                        }
                        Texture texture = new Texture(image);
                        textureCache.put(spriteId, texture);
                        return texture;
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load texture from sprite cache ID: " + spriteId, e);
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("Sprite not found in cache: " + spriteId));
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
