package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.ItemDefinition;
import dev.midnightcoder.cache.pickers.SpritePicker;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.function.Consumer;

public class ItemEditor implements Editor {
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox root = new VBox(10);
    private final ItemDefinition def;
    private final CacheManager cacheManager;
    private final ImageView preview = new ImageView();

    public ItemEditor(ItemDefinition def, CacheManager cacheManager) {
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
        rightBox.setMinWidth(300);
        // --- Left Side: Data ---
        leftBox.getChildren().add(new Label("Editing Item ID: " + def.getId()));
        leftBox.getChildren().add(createField("Name", def.getName(), def::setName));
        leftBox.getChildren().add(createField("Description", def.getDescription(), def::setDescription));

        var spriteIdField = new TextField(String.valueOf(def.getSpriteId()));
        spriteIdField.textProperty().addListener((obs, old, newVal) -> {
            try {
                def.setSpriteId(Integer.parseInt(newVal));
                updatePreview();
            }
            catch (NumberFormatException ignored) {
            }
        });

        var pickSpriteBtn = new Button("Pick Sprite");
        pickSpriteBtn.setOnAction(_ -> {
            var picker = new SpritePicker((Stage) root.getScene().getWindow(), cacheManager, def.getSpriteId());
            picker.showAndWait();
            picker.getResult().ifPresent(id -> {
                def.setSpriteId(id);
                spriteIdField.setText(String.valueOf(id));
                updatePreview();
            });
        });

        var spriteBox = new HBox(10);
        spriteBox.getChildren().addAll(new Label("Sprite ID:"), spriteIdField, pickSpriteBtn);
        leftBox.getChildren().add(spriteBox);

        leftBox.getChildren().add(createIntField("Value", def.getValue(), def::setValue));

        var tradeableBox = new CheckBox("Tradeable");
        tradeableBox.setSelected(def.isTradeable());
        tradeableBox.selectedProperty().addListener((_, _, newVal) -> def.setTradeable(newVal));

        leftBox.getChildren().add(tradeableBox);

        var equippableBox = new CheckBox("Equippable");
            equippableBox.setSelected(def.isEquippable());
            equippableBox.selectedProperty().addListener((_, _, newVal) -> def.setEquippable(newVal));

        leftBox.getChildren().add(equippableBox);

        // Backpack Actions
        leftBox.getChildren().add(new Label("Backpack Actions (max 5)"));
        for (int i = 0; i < 5; i++) {
            final int index = i;
            var f = new TextField(def.getBackpackActions()[i]);
            f.textProperty().addListener((_, _, newVal) -> def.getBackpackActions()[index] = newVal);
            leftBox.getChildren().add(f);
        }

        // Ground Actions
        leftBox.getChildren().add(new Label("Ground Actions (max 5)"));
        for (int i = 0; i < 5; i++) {
            final int index = i;
            var f = new TextField(def.getGroundActions()[i]);
            f.textProperty().addListener((_, _, newVal) -> def.getGroundActions()[index] = newVal);
            leftBox.getChildren().add(f);
        }

        // --- Right Side: Image and Bonuses ---
        preview.setFitWidth(256);
        preview.setFitHeight(256);
        preview.setPreserveRatio(true);
        rightBox.getChildren().add(new Label("Visual Preview:"));
        rightBox.getChildren().add(preview);
        updatePreview();

        var bonusPane = new TitledPane("Equipment Bonuses", createBonusGrid());
        bonusPane.setCollapsible(false);
        rightBox.getChildren().add(bonusPane);

        mainSplit.getChildren().addAll(leftBox, rightBox);
        root.getChildren().add(mainSplit);
    }

    private void updatePreview() {
        preview.setImage(null);
        if (def.getSpriteId() >= 0 && def.getSpriteId() < cacheManager.getSprites().size()) {
            var sprite = cacheManager.getSprites().get(def.getSpriteId());
            if (sprite.getPngData() != null && sprite.getPngData().length > 0) {
                try {
                    preview.setImage(new Image(new ByteArrayInputStream(sprite.getPngData())));
                }
                catch (Exception ignored) {
                }
            }
        }
    }

    private GridPane createBonusGrid() {
        var grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5));

        grid.add(new Label("Offensive"), 0, 0);
        grid.add(createIntField("Melee", def.getOffMelee(), def::setOffMelee), 0, 1);
        grid.add(createIntField("Ranged", def.getOffRanged(), def::setOffRanged), 0, 2);
        grid.add(createIntField("Magic", def.getOffMagic(), def::setOffMagic), 0, 3);

        grid.add(new Label("Defensive"), 1, 0);
        grid.add(createIntField("Melee", def.getDefMelee(), def::setDefMelee), 1, 1);
        grid.add(createIntField("Ranged", def.getDefRanged(), def::setDefRanged), 1, 2);
        grid.add(createIntField("Magic", def.getDefMagic(), def::setDefMagic), 1, 3);

        grid.add(new Label("Combat"), 0, 5);
        grid.add(createIntField("Accuracy", def.getOffAccuracy(), def::setOffAccuracy), 0, 6);
        grid.add(createIntField("Atk Speed", def.getAtkSpeed(), def::setAtkSpeed), 0, 7);
        grid.add(createIntField("Equip slot", def.getEquipSlot(), def::setEquipSlot), 1, 6);
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
            catch (NumberFormatException ignored) {
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
