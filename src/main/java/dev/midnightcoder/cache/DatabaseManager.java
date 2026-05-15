package dev.midnightcoder.cache;

import dev.midnightcoder.cache.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String DB_URL = "jdbc:sqlite:midnight_cache/cache.db";

    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            init();
        }
        catch (ClassNotFoundException | SQLException e) {
            log.error("Failed to initialize database: {}", e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void init() throws SQLException {
        try (var conn = getConnection(); var stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS sprites (" +
                "id INTEGER PRIMARY KEY," +
                "png_data BLOB," +
                "width INTEGER," +
                "height INTEGER)");

            stmt.execute("CREATE TABLE IF NOT EXISTS sprite_sheets (" +
                "id INTEGER PRIMARY KEY," +
                "sprite_id INTEGER," +
                "rows INTEGER," +
                "cols INTEGER," +
                "frame_width INTEGER," +
                "frame_height INTEGER)");

            stmt.execute("CREATE TABLE IF NOT EXISTS textures (" +
                "id INTEGER PRIMARY KEY," +
                "sprite_id INTEGER," +
                "sprite_sheet_id INTEGER," +
                "frame_index INTEGER," +
                "color_hex TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS items (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT," +
                "sprite_id INTEGER," +
                "off_accuracy INTEGER," +
                "off_melee INTEGER," +
                "off_ranged INTEGER," +
                "off_magic INTEGER," +
                "def_melee INTEGER," +
                "def_ranged INTEGER," +
                "def_magic INTEGER," +
                "atk_speed INTEGER," +
                "value INTEGER," +
                "tradeable INTEGER," +
                "backpack_actions TEXT," +
                "ground_actions TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS npcs (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT," +
                "sprite_sheet_id INTEGER," +
                "size INTEGER," +
                "combat_level INTEGER," +
                "actions TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS objects (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT," +
                "texture_id INTEGER," +
                "actions TEXT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS maps (" +
                "id TEXT PRIMARY KEY," +
                "file_name TEXT," +
                "png_data BLOB)");

            stmt.execute("CREATE TABLE IF NOT EXISTS audio (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "data BLOB," +
                "compressed_size INTEGER," +
                "duration REAL)");
        }
    }

    // Sprites
    public List<Sprite> loadSprites() throws SQLException {
        var list = new ArrayList<Sprite>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM sprites ORDER BY id")) {
            while (rs.next()) {
                list.add(new Sprite(
                    rs.getInt("id"),
                    rs.getBytes("png_data"),
                    rs.getInt("width"),
                    rs.getInt("height")
                ));
            }
        }
        return list;
    }

    public void saveSprites(List<Sprite> sprites) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO sprites (id, png_data, width, height) VALUES (?, ?, ?, ?)")) {
                for (Sprite s : sprites) {
                    pstmt.setInt(1, s.getId());
                    pstmt.setBytes(2, s.getPngData());
                    pstmt.setInt(3, s.getWidth());
                    pstmt.setInt(4, s.getHeight());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // SpriteSheets
    public List<SpriteSheet> loadSpriteSheets() throws SQLException {
        var list = new ArrayList<SpriteSheet>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM sprite_sheets ORDER BY id")) {
            while (rs.next()) {
                list.add(new SpriteSheet(
                    rs.getInt("id"),
                    rs.getInt("sprite_id"),
                    rs.getInt("rows"),
                    rs.getInt("cols"),
                    rs.getInt("frame_width"),
                    rs.getInt("frame_height")
                ));
            }
        }
        return list;
    }

    public void saveSpriteSheets(List<SpriteSheet> sheets) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO sprite_sheets (id, sprite_id, rows, cols, frame_width, frame_height) VALUES (?, ?, ?, ?, ?, ?)")) {
                for (SpriteSheet s : sheets) {
                    pstmt.setInt(1, s.getId());
                    pstmt.setInt(2, s.getSpriteId());
                    pstmt.setInt(3, s.getRows());
                    pstmt.setInt(4, s.getCols());
                    pstmt.setInt(5, s.getFrameWidth());
                    pstmt.setInt(6, s.getFrameHeight());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Textures
    public List<Texture> loadTextures() throws SQLException {
        var list = new ArrayList<Texture>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM textures ORDER BY id")) {
            while (rs.next()) {
                var t = new Texture(rs.getInt("id"), rs.getInt("sprite_id"), rs.getString("color_hex"));
                    t.setSpriteSheetId(rs.getInt("sprite_sheet_id"));
                    t.setFrameIndex(rs.getInt("frame_index"));
                list.add(t);
            }
        }
        return list;
    }

    public void saveTextures(List<Texture> textures) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO textures (id, sprite_id, sprite_sheet_id, frame_index, color_hex) VALUES (?, ?, ?, ?, ?)")) {
                for (Texture t : textures) {
                    pstmt.setInt(1, t.getId());
                    pstmt.setInt(2, t.getSpriteId());
                    pstmt.setInt(3, t.getSpriteSheetId());
                    pstmt.setInt(4, t.getFrameIndex());
                    pstmt.setString(5, t.getColorHex());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Items
    public List<ItemDefinition> loadItems() throws SQLException {
        var list = new ArrayList<ItemDefinition>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM items ORDER BY id")) {
            while (rs.next()) {
                var def = new ItemDefinition(rs.getInt("id"));
                    def.setName(rs.getString("name"));
                    def.setDescription(rs.getString("description"));
                    def.setSpriteId(rs.getInt("sprite_id"));
                    def.setOffAccuracy(rs.getInt("off_accuracy"));
                    def.setOffMelee(rs.getInt("off_melee"));
                    def.setOffRanged(rs.getInt("off_ranged"));
                    def.setOffMagic(rs.getInt("off_magic"));
                    def.setDefMelee(rs.getInt("def_melee"));
                    def.setDefRanged(rs.getInt("def_ranged"));
                    def.setDefMagic(rs.getInt("def_magic"));
                    def.setAtkSpeed(rs.getInt("atk_speed"));
                    def.setValue(rs.getInt("value"));
                    def.setTradeable(rs.getInt("tradeable") == 1);

                String bpActionsStr = rs.getString("backpack_actions");
                if (bpActionsStr != null) {
                    String[] bpActions = bpActionsStr.split("\\|", -1);
                    System.arraycopy(bpActions, 0, def.getBackpackActions(), 0, Math.min(bpActions.length, 5));
                }

                String grActionsStr = rs.getString("ground_actions");
                if (grActionsStr != null) {
                    String[] grActions = grActionsStr.split("\\|", -1);
                    System.arraycopy(grActions, 0, def.getGroundActions(), 0, Math.min(grActions.length, 5));
                }

                list.add(def);
            }
        }
        return list;
    }

    public void saveItems(List<ItemDefinition> items) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO items (id, name, description, sprite_id, off_accuracy, off_melee, off_ranged, off_magic, def_melee, def_ranged, def_magic, atk_speed, value, tradeable, backpack_actions, ground_actions) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                for (ItemDefinition def : items) {
                    pstmt.setInt(1, def.getId());
                    pstmt.setString(2, def.getName());
                    pstmt.setString(3, def.getDescription());
                    pstmt.setInt(4, def.getSpriteId());
                    pstmt.setInt(5, def.getOffAccuracy());
                    pstmt.setInt(6, def.getOffMelee());
                    pstmt.setInt(7, def.getOffRanged());
                    pstmt.setInt(8, def.getOffMagic());
                    pstmt.setInt(9, def.getDefMelee());
                    pstmt.setInt(10, def.getDefRanged());
                    pstmt.setInt(11, def.getDefMagic());
                    pstmt.setInt(12, def.getAtkSpeed());
                    pstmt.setInt(13, def.getValue());
                    pstmt.setInt(14, def.isTradeable() ? 1 : 0);
                    pstmt.setString(15, String.join("|", def.getBackpackActions()));
                    pstmt.setString(16, String.join("|", def.getGroundActions()));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // NPCs
    public List<NPCDefinition> loadNpcs() throws SQLException {
        var list = new ArrayList<NPCDefinition>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM npcs ORDER BY id")) {
            while (rs.next()) {
                var def = new NPCDefinition(rs.getInt("id"));
                    def.setName(rs.getString("name"));
                    def.setDescription(rs.getString("description"));
                    def.setSpriteSheetId(rs.getInt("sprite_sheet_id"));
                    def.setSize(rs.getInt("size"));
                    def.setCombatLevel(rs.getInt("combat_level"));

                String[] actions = rs.getString("actions").split("\\|", -1);
                System.arraycopy(actions, 0, def.getActions(), 0, Math.min(actions.length, 5));

                list.add(def);
            }
        }
        return list;
    }

    public void saveNpcs(List<NPCDefinition> npcs) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO npcs (id, name, description, sprite_sheet_id, size, combat_level, actions) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                for (NPCDefinition def : npcs) {
                    pstmt.setInt(1, def.getId());
                    pstmt.setString(2, def.getName());
                    pstmt.setString(3, def.getDescription());
                    pstmt.setInt(4, def.getSpriteSheetId());
                    pstmt.setInt(5, def.getSize());
                    pstmt.setInt(6, def.getCombatLevel());
                    pstmt.setString(7, String.join("|", def.getActions()));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Objects
    public List<ObjectDefinition> loadObjects() throws SQLException {
        var list = new ArrayList<ObjectDefinition>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM objects ORDER BY id")) {
            while (rs.next()) {
                var def = new ObjectDefinition(rs.getInt("id"));
                    def.setName(rs.getString("name"));
                    def.setDescription(rs.getString("description"));
                    def.setTextureId(rs.getInt("texture_id"));

                String[] actions = rs.getString("actions").split("\\|", -1);
                System.arraycopy(actions, 0, def.getActions(), 0, Math.min(actions.length, 5));

                list.add(def);
            }
        }
        return list;
    }

    public void saveObjects(List<ObjectDefinition> objects) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO objects (id, name, description, texture_id, actions) VALUES (?, ?, ?, ?, ?)")) {
                for (ObjectDefinition def : objects) {
                    pstmt.setInt(1, def.getId());
                    pstmt.setString(2, def.getName());
                    pstmt.setString(3, def.getDescription());
                    pstmt.setInt(4, def.getTextureId());
                    pstmt.setString(5, String.join("|", def.getActions()));
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Maps
    public List<MapDefinition> loadMaps() throws SQLException {
        var list = new ArrayList<MapDefinition>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM maps")) {
            while (rs.next()) {
                var def = new MapDefinition(UUID.fromString(rs.getString("id")), rs.getBytes("png_data"));
                    def.setFileName(rs.getString("file_name"));
                list.add(def);
            }
        }
        return list;
    }

    public void saveMaps(List<MapDefinition> maps) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO maps (id, file_name, png_data) VALUES (?, ?, ?)")) {
                for (MapDefinition def : maps) {
                    pstmt.setString(1, def.getId().toString());
                    pstmt.setString(2, def.getFileName());
                    pstmt.setBytes(3, def.getPngData());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Audio
    public List<AudioDefinition> loadAudio() throws SQLException {
        var list = new ArrayList<AudioDefinition>();
        try (var conn = getConnection();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT * FROM audio ORDER BY id")) {
            while (rs.next()) {
                list.add(new AudioDefinition(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBytes("data"),
                    rs.getLong("compressed_size"),
                    rs.getDouble("duration")
                ));
            }
        }
        return list;
    }

    public void saveAudio(List<AudioDefinition> audio) throws SQLException {
        try (var conn = getConnection()) {
            conn.setAutoCommit(false);
            try (var pstmt = conn.prepareStatement("INSERT OR REPLACE INTO audio (id, name, data, compressed_size, duration) VALUES (?, ?, ?, ?, ?)")) {
                for (var def : audio) {
                    pstmt.setInt(1, def.getId());
                    pstmt.setString(2, def.getName());
                    pstmt.setBytes(3, def.getData());
                    pstmt.setLong(4, def.getCompressedSize());
                    pstmt.setDouble(5, def.getDuration());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
