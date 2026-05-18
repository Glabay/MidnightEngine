/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-13
 */
module MidnightRPG.MidnightEngine.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires org.slf4j;
    requires org.xerial.sqlitejdbc;

    opens dev.midnightcoder.cache to javafx.fxml;
    exports dev.midnightcoder.cache;
    exports dev.midnightcoder.cache.editors;
    opens dev.midnightcoder.cache.editors to javafx.fxml;
    exports dev.midnightcoder.cache.pickers;
    opens dev.midnightcoder.cache.pickers to javafx.fxml;
    exports dev.midnightcoder.cache.model;
    exports dev.midnightcoder.engine.renderer.graphics;
    exports dev.midnightcoder.engine.entity;
}