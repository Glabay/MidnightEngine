package dev.midnightcoder.cache.pickers;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.SpriteSheet;
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

public class SpriteSheetPicker extends Stage {
    private final CacheManager cacheManager;
    private int currentPage = 0;
    private final int ITEMS_PER_PAGE = 20;
    private final int COLS = 5;
    private Integer selectedId;

    private final GridPane grid = new GridPane();
    private final Label pageLabel = new Label();
    private final Button prevBtn = new Button("< Previous");
    private final Button nextBtn = new Button("Next >");

    public SpriteSheetPicker(Stage owner, CacheManager cacheManager, int initialId) {
        this.cacheManager = cacheManager;
        this.selectedId = initialId;
        
        if (initialId >= 0) {
            this.currentPage = initialId / ITEMS_PER_PAGE;
        }

        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        setTitle("Select SpriteSheet");

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
            if ((currentPage + 1) * ITEMS_PER_PAGE < cacheManager.getSpriteSheets().size()) {
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
        List<SpriteSheet> sheets = cacheManager.getSpriteSheets();
        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, sheets.size());

        for (var i = startIndex; i < endIndex; i++) {
            var id = i;
            var sheet = sheets.get(i);
            
            var box = new VBox(5);
                box.setAlignment(Pos.CENTER);
                box.setPadding(new Insets(5));
                box.getStyleClass().add("picker-box");
            if (selectedId != null && selectedId == id)
                box.getStyleClass().add("picker-box-selected");

            var iv = new ImageView();
                iv.setFitWidth(64);
                iv.setFitHeight(64);
                iv.setPreserveRatio(true);

            int spriteId = sheet.getSpriteId();
            if (spriteId >= 0 && spriteId < cacheManager.getSprites().size()) {
                var sprite = cacheManager.getSprites().get(spriteId);
                if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                    try {
                        iv.setImage(new Image(new ByteArrayInputStream(sprite.getPngData())));
                    }
                    catch (Exception _) {}
                }
            }

            box.getChildren().addAll(iv, new Label("ID: " + id));

            box.setOnMouseClicked(_ -> {
                this.selectedId = id;
                close();
            });

            int row = (i - startIndex) / COLS;
            int col = (i - startIndex) % COLS;
            grid.add(box, col, row);
        }

        int totalPages = (int) Math.ceil((double) sheets.size() / ITEMS_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        pageLabel.setText(String.format("Page %d of %d", currentPage + 1, totalPages));
        prevBtn.setDisable(currentPage == 0);
        nextBtn.setDisable(currentPage >= totalPages - 1);
    }

    public Optional<Integer> getResult() {
        return Optional.ofNullable(selectedId);
    }
}
