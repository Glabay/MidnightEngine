package dev.midnightcoder.cache.pickers;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.SpriteSheet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.Optional;

public class SpriteSheetFramePicker extends Stage {
    private final CacheManager cacheManager;
    private final SpriteSheet sheet;
    private Integer selectedFrame;

    private final GridPane grid = new GridPane();

    public SpriteSheetFramePicker(Stage owner, CacheManager cacheManager, SpriteSheet sheet, Integer initialFrame) {
        this.cacheManager = cacheManager;
        this.sheet = sheet;
        this.selectedFrame = initialFrame;

        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Select Frame from SpriteSheet " + sheet.getId());

        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        var scrollPane = new ScrollPane(grid);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefViewportHeight(500);
            scrollPane.setPrefViewportWidth(600);

        refreshGrid();

        var root = new VBox(scrollPane);
            root.setAlignment(Pos.CENTER);

        setScene(new Scene(root, 650, 550));
    }

    private void refreshGrid() {
        grid.getChildren().clear();
        int spriteId = sheet.getSpriteId();
        
        if (spriteId < 0 || spriteId >= cacheManager.getSprites().size()) {
            grid.add(new Label("Invalid Sprite ID"), 0, 0);
            return;
        }

        var sprite = cacheManager.getSprites().get(spriteId);
        if (sprite.getPngData() == null || sprite.getPngData().length == 0) {
            grid.add(new Label("No Sprite Data"), 0, 0);
            return;
        }

        try {
            var fullImage = new Image(new ByteArrayInputStream(sprite.getPngData()));
            var rows = sheet.getRows();
            var cols = sheet.getCols();
            var fw = sheet.getFrameWidth();
            var fh = sheet.getFrameHeight();

            if (rows <= 0 || cols <= 0 || fw <= 0 || fh <= 0) {
                grid.add(new Label("Invalid Sheet Dimensions"), 0, 0);
                return;
            }

            for (var r = 0; r < rows; r++) {
                for (var c = 0; c < cols; c++) {
                    var frameIndex = r * cols + c;
                    
                    var box = new VBox(5);
                        box.setAlignment(Pos.CENTER);
                        box.setPadding(new Insets(5));
                        box.setStyle("-fx-border-color: #cccccc; -fx-cursor: hand;");
                    if (selectedFrame != null && selectedFrame == frameIndex)
                        box.setStyle("-fx-border-color: #0078d7; -fx-border-width: 2px; -fx-background-color: #e5f1fb; -fx-cursor: hand;");

                    var frameView = new ImageView(fullImage);
                        frameView.setViewport(new Rectangle2D(c * fw, r * fh, fw, fh));

                    if (fw > 128 || fh > 128) {
                        frameView.setFitWidth(128);
                        frameView.setFitHeight(128);
                        frameView.setPreserveRatio(true);
                    }

                    box.getChildren().addAll(frameView, new Label("Frame " + frameIndex));
                    
                    box.setOnMouseClicked(_ -> {
                        selectedFrame = frameIndex;
                        close();
                    });

                    grid.add(box, c, r);
                }
            }
        }
        catch (Exception e) {
            grid.add(new Label("Error loading image: " + e.getMessage()), 0, 0);
        }
    }

    public Optional<Integer> getResult() {
        return Optional.ofNullable(selectedFrame);
    }
}
