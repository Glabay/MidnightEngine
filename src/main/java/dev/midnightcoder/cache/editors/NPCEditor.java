package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.NPCDefinition;
import dev.midnightcoder.cache.pickers.SpriteSheetPicker;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

public class NPCEditor implements Editor {
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox root = new VBox(10);
    private final NPCDefinition def;
    private final CacheManager cacheManager;
    private final ImageView preview = new ImageView();

    public NPCEditor(NPCDefinition def, CacheManager cacheManager) {
        this.def = def;
        this.cacheManager = cacheManager;
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        refresh();
    }

    private void refresh() {
        root.getChildren().clear();
        root.setSpacing(10);

        var mainSplit = new HBox(20);
        mainSplit.setPadding(new Insets(10));

        var leftBox = new VBox(10);
        var rightBox = new VBox(10);
        HBox.setHgrow(leftBox, Priority.ALWAYS);
        rightBox.setMinWidth(320);

        // --- Left Side: General Info ---
        leftBox.getChildren().add(new Label("Editing NPC ID: " + def.getId()));

        leftBox.getChildren().add(createField("Name", def.getName(), def::setName));
        leftBox.getChildren().add(createField("Description", def.getDescription(), def::setDescription));

        var spriteSheetIdField = new TextField(String.valueOf(def.getSpriteSheetId()));
        spriteSheetIdField.textProperty().addListener((_, _, newVal) -> {
            try {
                def.setSpriteSheetId(Integer.parseInt(newVal));
                updatePreview();
            }
            catch (NumberFormatException ignored) {
            }
        });

        var pickSpriteSheetBtn = new Button("Pick SpriteSheet");
        pickSpriteSheetBtn.setOnAction(_ -> {
            var picker = new SpriteSheetPicker((Stage) root.getScene().getWindow(), cacheManager, def.getSpriteSheetId());
            picker.showAndWait();
            picker.getResult().ifPresent(id -> {
                def.setSpriteSheetId(id);
                spriteSheetIdField.setText(String.valueOf(id));
                updatePreview();
            });
        });

        var spriteSheetBox = new HBox(10);
        spriteSheetBox.getChildren().addAll(new Label("SpriteSheet ID:"), spriteSheetIdField, pickSpriteSheetBtn);
        leftBox.getChildren().add(spriteSheetBox);

        leftBox.getChildren().add(createIntField("Combat Level", def.getCombatLevel(), def::setCombatLevel));
        leftBox.getChildren().add(createIntField("Size (tiles)", def.getSize(), def::setSize));

        leftBox.getChildren().add(new Label("Actions (max 5)"));
        for (int i = 0; i < 5; i++) {
            final int index = i;
            var f = new TextField(def.getActions()[i]);
            f.textProperty().addListener((_, _, newVal) -> def.getActions()[index] = newVal);
            leftBox.getChildren().add(f);
        }

        // --- Right Side: Preview & Combat Section ---
        preview.setFitWidth(128);
        preview.setFitHeight(128);
        preview.setPreserveRatio(true);
        rightBox.getChildren().add(new Label("Visual Preview:"));
        rightBox.getChildren().add(preview);
        updatePreview();

        var combatPane = new TitledPane("Combat", createCombatGrid());
        combatPane.setCollapsible(false);
        rightBox.getChildren().add(combatPane);

        mainSplit.getChildren().addAll(leftBox, rightBox);
        root.getChildren().add(mainSplit);
    }

    private void updatePreview() {
        preview.setImage(null);
        int sheetId = def.getSpriteSheetId();
        if (sheetId >= 0 && sheetId < cacheManager.getSpriteSheets().size()) {
            var sheet = cacheManager.getSpriteSheets().get(sheetId);
            if (sheet != null && sheet.getSpriteId() >= 0 && sheet.getSpriteId() < cacheManager.getSprites().size()) {
                var sprite = cacheManager.getSprites().get(sheet.getSpriteId());
                if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                    try {
                        var fullImage = new Image(new ByteArrayInputStream(sprite.getPngData()));
                        int fw = sheet.getFrameWidth() > 0 ? sheet.getFrameWidth() : (int) fullImage.getWidth();
                        int fh = sheet.getFrameHeight() > 0 ? sheet.getFrameHeight() : (int) fullImage.getHeight();
                        preview.setImage(fullImage);
                        preview.setViewport(new Rectangle2D(0, 0, fw, fh));
                    }
                    catch (Exception ignored) {
                    }
                }
            }
        }
    }

    private GridPane createCombatGrid() {
        var grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5));

        grid.add(new Label("Combat Attributes"), 0, 0);
        grid.add(createIntField("Attack Speed", def.getAttackSpeed(), def::setAttackSpeed), 0, 1);
        grid.add(createIntField("Health", def.getHealth(), def::setHealth), 0, 2);
        grid.add(createIntField("Death Delay", def.getDeathDelay(), def::setDeathDelay), 0, 3);

        grid.add(new Label("Combat Stats"), 0, 4);
        grid.add(createIntField("Attack", def.getAttack(), def::setAttack), 0, 5);
        grid.add(createIntField("Strength", def.getStrength(), def::setStrength), 0, 6);
        grid.add(createIntField("Defence", def.getDefence(), def::setDefence), 0, 7);

        grid.add(new Label("Specialized"), 1, 4);
        grid.add(createIntField("Ranged", def.getRanged(), def::setRanged), 1, 5);
        grid.add(createIntField("Magic", def.getMagic(), def::setMagic), 1, 6);

        return grid;
    }

    private HBox createField(String label, String initial, Consumer<String> consumer) {
        var f = new TextField(initial);
        f.textProperty().addListener((_, _, newVal) -> consumer.accept(newVal));
        HBox.setHgrow(f, Priority.ALWAYS);
        return new HBox(5, new Label(label + ":"), f);
    }

    private HBox createIntField(String label, int initial, Consumer<Integer> consumer) {
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
        return scrollPane;
    }

    @Override
    public void save() {
    }
}
