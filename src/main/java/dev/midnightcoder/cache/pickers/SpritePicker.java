package dev.midnightcoder.cache.pickers;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.Sprite;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public class SpritePicker extends Stage {
    private final CacheManager cacheManager;
    private int currentPage = 0;
    private final int SPRITES_PER_PAGE = 20;
    private final int COLS = 5;
    private final int ROWS = 4;
    private Integer selectedSpriteId;

    private final GridPane grid = new GridPane();
    private final Label pageLabel = new Label();
    private final Button prevBtn = new Button("< Previous");
    private final Button nextBtn = new Button("Next >");

    public SpritePicker(Stage owner, CacheManager cacheManager, int initialSpriteId) {
        this.cacheManager = cacheManager;
        this.selectedSpriteId = initialSpriteId;
        
        if (initialSpriteId >= 0) {
            this.currentPage = initialSpriteId / SPRITES_PER_PAGE;
        }

        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Select Sprite");

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        prevBtn.setOnAction(_ -> {
            if (currentPage > 0) {
                currentPage--;
                refreshGrid();
            }
        });

        nextBtn.setOnAction(_ -> {
            if ((currentPage + 1) * SPRITES_PER_PAGE < cacheManager.getSprites().size()) {
                currentPage++;
                refreshGrid();
            }
        });

        var nav = new HBox(10, prevBtn, pageLabel, nextBtn);
            nav.setAlignment(Pos.CENTER);
            nav.setPadding(new Insets(10));

        var root = new VBox(grid, nav);
            root.setAlignment(Pos.CENTER);

        refreshGrid();

        var scene = new Scene(root, 600, 500);
        dev.midnightcoder.cache.EditorSettings.applyTheme(scene);
        setScene(scene);
    }

    private void refreshGrid() {
        grid.getChildren().clear();
        List<Sprite> sprites = cacheManager.getSprites();
        int startIndex = currentPage * SPRITES_PER_PAGE;
        int endIndex = Math.min(startIndex + SPRITES_PER_PAGE, sprites.size());

        for (int i = startIndex; i < endIndex; i++) {
            int spriteId = i;
            var sprite = sprites.get(i);
            
            var spriteBox = new VBox(5);
                spriteBox.setAlignment(Pos.CENTER);
                spriteBox.setPadding(new Insets(5));
                spriteBox.getStyleClass().add("picker-box");
            if (selectedSpriteId != null && selectedSpriteId == spriteId)
                spriteBox.getStyleClass().add("picker-box-selected");

            var iv = new ImageView();
                iv.setFitWidth(64);
                iv.setFitHeight(64);
                iv.setPreserveRatio(true);

            if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                try {
                    iv.setImage(new Image(new ByteArrayInputStream(sprite.getPngData())));
                }
                catch (Exception _) {}
            }

            spriteBox.getChildren().addAll(iv, new Label("ID: " + spriteId));

            spriteBox.setOnMouseClicked(_ -> {
                selectedSpriteId = spriteId;
                close();
            });

            int row = (i - startIndex) / COLS;
            int col = (i - startIndex) % COLS;
            grid.add(spriteBox, col, row);
        }

        int totalPages = (int) Math.ceil((double) sprites.size() / SPRITES_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        pageLabel.setText(String.format("Page %d of %d", currentPage + 1, totalPages));
        prevBtn.setDisable(currentPage == 0);
        nextBtn.setDisable(currentPage >= totalPages - 1);
    }

    public Optional<Integer> getResult() {
        return Optional.ofNullable(selectedSpriteId);
    }
}
