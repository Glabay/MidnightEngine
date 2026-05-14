package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.SpriteSheet;
import dev.midnightcoder.cache.pickers.SpritePicker;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

public class SpriteSheetEditor implements Editor {
    private final HBox root = new HBox(20);
    private final VBox detailsBox = new VBox(10);
    private final SpriteSheet sheet;
    private final CacheManager cacheManager;
    private final ImageView spritePreview = new ImageView();
    private final GridPane gridView = new GridPane();
    private final ScrollPane scrollPane = new ScrollPane(gridView);

    public SpriteSheetEditor(SpriteSheet sheet, CacheManager cacheManager) {
        this.sheet = sheet;
        this.cacheManager = cacheManager;
        root.setPadding(new Insets(10));
        refresh();
    }

    private void refresh() {
        detailsBox.getChildren().clear();
        root.getChildren().clear();

        detailsBox.getChildren().add(new Label("Editing SpriteSheet ID: " + sheet.getId()));

        detailsBox.getChildren().add(createIntField("Sprite ID", sheet.getSpriteId(), id -> {
            sheet.setSpriteId(id);
            updatePreview();
        }));

        var searchBtn = new Button("Search Sprite");
        searchBtn.setOnAction(_ -> openSpritePicker());
        detailsBox.getChildren().add(searchBtn);

        spritePreview.setFitWidth(200);
        spritePreview.setFitHeight(200);
        spritePreview.setPreserveRatio(true);
        detailsBox.getChildren().add(new Label("Sprite Preview:"));
        detailsBox.getChildren().add(spritePreview);

        detailsBox.getChildren().add(createIntField("Rows", sheet.getRows(), sheet::setRows));
        detailsBox.getChildren().add(createIntField("Columns", sheet.getCols(), sheet::setCols));
        detailsBox.getChildren().add(createIntField("Frame Width", sheet.getFrameWidth(), sheet::setFrameWidth));
        detailsBox.getChildren().add(createIntField("Frame Height", sheet.getFrameHeight(), sheet::setFrameHeight));

        gridView.setHgap(5);
        gridView.setVgap(5);
        gridView.setPadding(new Insets(5));
        scrollPane.setPrefHeight(600);
        scrollPane.setPrefWidth(600);

        var gridContainer = new VBox(10);
        gridContainer.getChildren().add(new Label("Grid View:"));
        gridContainer.getChildren().add(scrollPane);

        root.getChildren().addAll(detailsBox, gridContainer);

        updatePreview();
    }

    private void updatePreview() {
        int spriteId = sheet.getSpriteId();
        gridView.getChildren().clear();

        if (spriteId >= 0 && spriteId < cacheManager.getSprites().size()) {
            var sprite = cacheManager.getSprites().get(spriteId);
            if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                try {
                    var fullImage = new Image(new ByteArrayInputStream(sprite.getPngData()));
                    spritePreview.setImage(fullImage);

                    int rows = sheet.getRows();
                    int cols = sheet.getCols();
                    int fw = sheet.getFrameWidth();
                    int fh = sheet.getFrameHeight();

                    if (rows > 0 && cols > 0 && fw > 0 && fh > 0) {
                        for (int r = 0; r < rows; r++) {
                            for (int c = 0; c < cols; c++) {
                                var frameView = new ImageView(fullImage);
                                frameView.setViewport(new Rectangle2D(c * fw, r * fh, fw, fh));
                                gridView.add(frameView, c, r);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    spritePreview.setImage(null);
                }
            }
            else {
                spritePreview.setImage(null);
            }
        }
        else {
            spritePreview.setImage(null);
        }
    }

    private void openSpritePicker() {
        var picker = new SpritePicker((Stage) root.getScene().getWindow(), cacheManager, sheet.getSpriteId());
        picker.showAndWait();
        picker.getResult().ifPresent(id -> {
            sheet.setSpriteId(id);
            refresh();
        });
    }

    private HBox createIntField(String label, int initial, Consumer<Integer> consumer) {
        var f = new TextField(String.valueOf(initial));
        f.textProperty().addListener((obs, old, newVal) -> {
            try {
                consumer.accept(Integer.parseInt(newVal));
                updatePreview();
            }
            catch (NumberFormatException ignored) {
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
