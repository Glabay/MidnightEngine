package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.EditorSettings;
import dev.midnightcoder.cache.model.MapDefinition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class MapEditor implements Editor {
    private static final Logger log = LoggerFactory.getLogger(MapEditor.class);
    private final VBox root = new VBox(10);
    private final MapDefinition map;

    public MapEditor(MapDefinition map) {
        this.map = map;
        root.setPadding(new Insets(10));
        refresh();
    }

    private void refresh() {
        root.getChildren().clear();
        root.getChildren().add(new Label("Map UUID:"));
        root.getChildren().add(new TextField(map.getId().toString()));
        root.getChildren().add(new Label("Map Name: " + map.getFileName()));

        if (map.getPngData() != null && map.getPngData().length > 0) {
            try {
                var img = new Image(new ByteArrayInputStream(map.getPngData()));
                var iv = new ImageView(img);
                iv.setFitWidth(500);
                iv.setFitHeight(500);
                iv.setPreserveRatio(true);
                root.getChildren().add(iv);
            }
            catch (Exception e) {
                log.error("Error loading map image: {}", e.getMessage(), e);
                root.getChildren().add(new Label("Error loading map image: " + e.getMessage()));
            }
        }
        else root.getChildren().add(new Label("No Map Data (PNG)"));

        root.getChildren().add(getReplaceBtn());
    }

    private Button getReplaceBtn() {
        var replaceBtn = new Button("Replace Map PNG");
        replaceBtn.setOnAction(_ -> {
            var fileChooser = new FileChooser();
            fileChooser.setTitle("Select Map PNG");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            fileChooser.setInitialDirectory(EditorSettings.getLastOpenedDirectory());

            var selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
            if (selectedFile != null) {
                EditorSettings.setLastOpenedDirectory(selectedFile.getParentFile());
                try {
                    byte[] data = Files.readAllBytes(selectedFile.toPath());
                    map.setPngData(data);
                    map.setFileName(selectedFile.getName());
                    refresh();
                }
                catch (Exception ex) {
                    log.error("Error loading map PNG: {}", ex.getMessage(), ex);
                }
            }
        });
        return replaceBtn;
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void save() {
    }
}
