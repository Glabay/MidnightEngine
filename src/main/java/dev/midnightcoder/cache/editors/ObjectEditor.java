package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.ObjectDefinition;
import dev.midnightcoder.cache.pickers.TexturePicker;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class ObjectEditor implements Editor {
    private final VBox root = new VBox(10);

    public ObjectEditor(ObjectDefinition def, CacheManager cacheManager) {
        root.setPadding(new Insets(10));

        root.getChildren().add(new Label("Editing Object ID: " + def.getId()));

        root.getChildren().add(createField("Name", def.getName(), def::setName));
        root.getChildren().add(createField("Description", def.getDescription(), def::setDescription));

        var textureIdBox = createIntField("Texture ID", def.getTextureId(), def::setTextureId);
        var pickTextureBtn = new Button("Pick Texture");
        pickTextureBtn.setOnAction(_ -> {
            var picker = new TexturePicker((Stage) root.getScene().getWindow(), cacheManager, def.getTextureId());
            picker.showAndWait();
            picker.getResult().ifPresent(id -> {
                def.setTextureId(id);
                ((TextField) textureIdBox.getChildren().get(1)).setText(String.valueOf(id));
            });
        });
        textureIdBox.getChildren().add(pickTextureBtn);
        root.getChildren().add(textureIdBox);

        root.getChildren().add(new Label("Actions (max 5)"));
        for (int i = 0; i < 5; i++) {
            final int index = i;
            var f = new TextField(def.getActions()[index]);
            f.textProperty().addListener((_, _, newVal) -> def.getActions()[index] = newVal);
            root.getChildren().add(f);
        }
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
        return root;
    }

    @Override
    public void save() {
    }
}
