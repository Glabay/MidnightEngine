package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.Texture;
import dev.midnightcoder.cache.pickers.SpritePicker;
import dev.midnightcoder.cache.pickers.SpriteSheetFramePicker;
import dev.midnightcoder.cache.pickers.SpriteSheetPicker;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class TextureEditor implements Editor {
    private final VBox root = new VBox(10);
    private final Texture texture;
    private final CacheManager cacheManager;
    private final ImageView preview = new ImageView();

    public TextureEditor(Texture texture, CacheManager cacheManager) {
        this.texture = texture;
        this.cacheManager = cacheManager;
        root.setPadding(new Insets(10));
        refresh();
    }

    private void refresh() {
        root.getChildren().clear();
        root.getChildren().add(new Label("Editing Texture ID: " + texture.getId()));

        var spriteIdBox = createIntField("Sprite ID", texture.getSpriteId(), id -> {
            texture.setSpriteId(id);
            refresh();
        });
        var pickSpriteBtn = new Button("Pick Sprite");
        pickSpriteBtn.setOnAction(_ -> {
            var picker = new SpritePicker((Stage) root.getScene().getWindow(), cacheManager, texture.getSpriteId());
            picker.showAndWait();
            picker.getResult().ifPresent(id -> {
                texture.setSpriteId(id);
                refresh();
            });
        });

        var spriteBox = new HBox(10);
        spriteBox.getChildren().addAll(spriteIdBox, pickSpriteBtn);
        root.getChildren().add(spriteBox);

        var sheetIdBox = createIntField("SpriteSheet ID", texture.getSpriteSheetId(), id -> {
            texture.setSpriteSheetId(id);
            texture.setFrameIndex(0); // Reset frame index when sheet changes
            refresh();
        });
        var pickSheetBtn = new Button("Pick SpriteSheet");
        pickSheetBtn.setOnAction(_ -> {
            var picker = new SpriteSheetPicker((Stage) root.getScene().getWindow(), cacheManager, texture.getSpriteSheetId());
            picker.showAndWait();
            picker.getResult().ifPresent(id -> {
                texture.setSpriteSheetId(id);
                texture.setFrameIndex(0);
                texture.setSpriteId(-1); // Unset individual sprite
                refresh();
            });
        });
        var sheetBox = new HBox(10);
        sheetBox.getChildren().addAll(sheetIdBox, pickSheetBtn);
        root.getChildren().add(sheetBox);

        if (texture.getSpriteSheetId() >= 0) {
            var frameIndexBox = createIntField("Frame Index", texture.getFrameIndex(), idx -> {
                texture.setFrameIndex(idx);
                updatePreview();
            });
            var pickFrameBtn = new Button("Pick Frame");
            pickFrameBtn.setOnAction(_ -> {
                int sid = texture.getSpriteSheetId();
                if (sid >= 0 && sid < cacheManager.getSpriteSheets().size()) {
                    var sheet = cacheManager.getSpriteSheets().get(sid);
                    var picker = new SpriteSheetFramePicker((Stage) root.getScene().getWindow(), cacheManager, sheet, texture.getFrameIndex());
                    picker.showAndWait();
                    picker.getResult().ifPresent(idx -> {
                        texture.setFrameIndex(idx);
                        refresh();
                    });
                }
            });
            var frameBox = new HBox(10);
            frameBox.getChildren().addAll(frameIndexBox, pickFrameBtn);
            root.getChildren().add(frameBox);
        }

        root.getChildren().add(createField("Color Hex", texture.getColorHex(), texture::setColorHex));

        preview.setFitWidth(128);
        preview.setFitHeight(128);
        preview.setPreserveRatio(true);
        root.getChildren().add(new Label("Visual Preview:"));
        root.getChildren().add(preview);
        updatePreview();
    }

    private void updatePreview() {
        preview.setImage(null);
        preview.setViewport(null);

        if (texture.getSpriteId() >= 0 && texture.getSpriteId() < cacheManager.getSprites().size()) {
            var sprite = cacheManager.getSprites().get(texture.getSpriteId());
            if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                try {
                    preview.setImage(new Image(new ByteArrayInputStream(sprite.getPngData())));
                }
                catch (Exception _) {
                }
            }
        }
        else if (texture.getSpriteSheetId() >= 0 && texture.getSpriteSheetId() < cacheManager.getSpriteSheets().size()) {
            var sheet = cacheManager.getSpriteSheets().get(texture.getSpriteSheetId());
            int sid = sheet.getSpriteId();
            if (sid >= 0 && sid < cacheManager.getSprites().size()) {
                var sprite = cacheManager.getSprites().get(sid);
                if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                    try {
                        var fullImage = new Image(new ByteArrayInputStream(sprite.getPngData()));
                        int r = texture.getFrameIndex() / sheet.getCols();
                        int c = texture.getFrameIndex() % sheet.getCols();

                        preview.setImage(fullImage);
                        preview.setViewport(new Rectangle2D(c * sheet.getFrameWidth(), r * sheet.getFrameHeight(), sheet.getFrameWidth(), sheet.getFrameHeight()));
                    }
                    catch (Exception _) {
                    }
                }
            }
        }
    }

    private HBox createField(String label, String initial, java.util.function.Consumer<String> consumer) {
        var f = new TextField(initial);
        f.textProperty().addListener((_, _, newVal) -> consumer.accept(newVal));
        HBox.setHgrow(f, Priority.ALWAYS);
        return new HBox(5, new Label(label + ":"), f);
    }

    private HBox createIntField(String label, int initial, java.util.function.Consumer<Integer> consumer) {
        var f = new TextField(String.valueOf(initial));
        f.textProperty().addListener((_, _, newVal) -> {
            try {
                consumer.accept(Integer.parseInt(newVal));
            }
            catch (NumberFormatException _) {
            }
        });
        return new HBox(5, new Label(label + ":"), f);
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void save() {
    }
}
