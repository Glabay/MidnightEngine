package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.EditorSettings;
import dev.midnightcoder.cache.model.Sprite;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class SpriteEditor implements Editor {
    private static final Logger log = LoggerFactory.getLogger(SpriteEditor.class);
    private final VBox root = new VBox(10);
    private final Sprite sprite;

    public SpriteEditor(Sprite sprite) {
        this.sprite = sprite;
        root.setPadding(new Insets(10));
        refresh();
    }

    private void refresh() {
        root.getChildren().clear();
        root.getChildren().add(new Label("Sprite ID: " + sprite.getId()));

        if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
            try {
                var img = new Image(new ByteArrayInputStream(sprite.getPngData()));
                var iv = new ImageView(img);
                iv.setFitWidth(300);
                iv.setFitHeight(300);
                iv.setPreserveRatio(true);
                root.getChildren().add(iv);
            }
            catch (Exception e) {
                log.error("Error loading image for sprite ID {}: {}", sprite.getId(), e.getMessage());
                root.getChildren().add(new Label("Error loading image: " + e.getMessage()));
            }
        }
        else root.getChildren().add(new Label("No Image Data"));


        root.getChildren().add(new Label("Dimensions: " + sprite.getWidth() + "x" + sprite.getHeight()));
        root.getChildren().add(new Label("Memory usage: " + (sprite.getMemoryUsage() / 1024) + " KB"));

        root.getChildren().add(getReplaceBtn());
    }

    private Button getReplaceBtn() {
        var replaceBtn = new Button("Replace PNG");
        replaceBtn.setOnAction(_ -> {
            var fileChooser = new FileChooser();
            fileChooser.setTitle("Select Sprite PNG");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
            fileChooser.setInitialDirectory(EditorSettings.getLastOpenedDirectory());

            var selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
            if (selectedFile != null) {
                EditorSettings.setLastOpenedDirectory(selectedFile.getParentFile());
                try {
                    byte[] data = Files.readAllBytes(selectedFile.toPath());
                    var tempImg = new Image(new ByteArrayInputStream(data));

                    sprite.setPngData(data);
                    sprite.setWidth((int) tempImg.getWidth());
                    sprite.setHeight((int) tempImg.getHeight());

                    refresh();
                }
                catch (Exception ex) {
                    log.error("Error replacing sprite PNG: {}", ex.getMessage(), ex);
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
