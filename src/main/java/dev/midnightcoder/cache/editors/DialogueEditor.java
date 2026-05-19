package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.DialogueDefinition;
import dev.midnightcoder.cache.model.DialogueFrameDefinition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DialogueEditor implements Editor {
    private final VBox root = new VBox(10);
    private final DialogueDefinition def;
    private final CacheManager cacheManager;
    private final VBox framesList = new VBox(5);

    public DialogueEditor(DialogueDefinition def, CacheManager cacheManager) {
        this.def = def;
        this.cacheManager = cacheManager;
        root.setPadding(new Insets(10));

        root.getChildren().add(new Label("Editing Dialogue ID: " + def.getId()));

        var dialogueIdField = new TextField(def.getDialogueId());
        dialogueIdField.textProperty().addListener((_, _, newVal) -> def.setDialogueId(newVal));
        root.getChildren().add(new HBox(5, new Label("Dialogue Name ID:"), dialogueIdField));

        var startFrameField = new TextField(def.getStartFrameId());
        startFrameField.textProperty().addListener((_, _, newVal) -> def.setStartFrameId(newVal));
        root.getChildren().add(new HBox(5, new Label("Start Frame ID:"), startFrameField));

        root.getChildren().add(new Separator());
        root.getChildren().add(new Label("Frames"));

        var scroll = new ScrollPane(framesList);
        scroll.setFitToWidth(true);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.getChildren().add(scroll);

        var addFrameBtn = new Button("Add Frame");
        addFrameBtn.setOnAction(e -> {
            var frame = new DialogueFrameDefinition();
            frame.setId("frame_" + def.getFrames().size());
            frame.setType("npc");
            def.getFrames().add(frame);
            refreshFrames();
        });
        root.getChildren().add(addFrameBtn);

        refreshFrames();
    }

    private void refreshFrames() {
        framesList.getChildren().clear();
        for (int i = 0; i < def.getFrames().size(); i++) {
            var frame = def.getFrames().get(i);
            framesList.getChildren().add(createFrameEditor(frame, i));
        }
    }

    private Node createFrameEditor(DialogueFrameDefinition frame, int index) {
        var box = new TitledPane("Frame: " + frame.getId(), new VBox(5));
        box.setExpanded(false);
        var content = (VBox) box.getContent();

        var idField = new TextField(frame.getId());
        idField.textProperty().addListener((obs, old, newVal) -> {
            frame.setId(newVal);
            box.setText("Frame: " + newVal);
        });
        content.getChildren().add(new HBox(5, new Label("ID:"), idField));

        var typeCombo = new ComboBox<>(FXCollections.observableArrayList("npc", "player", "choice", "end", "message", "item"));
        typeCombo.setValue(frame.getType());
        typeCombo.setOnAction(e -> frame.setType(typeCombo.getValue()));
        content.getChildren().add(new HBox(5, new Label("Type:"), typeCombo));

        var speakerField = new TextField(frame.getSpeaker());
        speakerField.textProperty().addListener((obs, old, newVal) -> frame.setSpeaker(newVal));
        content.getChildren().add(new HBox(5, new Label("Speaker:"), speakerField));

        var textField = new TextArea(frame.getText());
        textField.setPrefRowCount(2);
        textField.textProperty().addListener((obs, old, newVal) -> frame.setText(newVal));
        content.getChildren().add(new VBox(5, new Label("Text:"), textField));

        var nextField = new TextField(frame.getNext());
        nextField.textProperty().addListener((obs, old, newVal) -> frame.setNext(newVal));
        content.getChildren().add(new HBox(5, new Label("Next Frame ID:"), nextField));

        var choicesArea = new TextArea();
        choicesArea.setPromptText("Choice Text:NextID (one per line)");
        choicesArea.setPrefRowCount(3);
        choicesArea.setText(frame.getChoices().stream()
                .map(c -> c.getText() + ":" + (c.getNextId() == null ? "" : c.getNextId()))
                .collect(Collectors.joining("\n")));
        choicesArea.textProperty().addListener((obs, old, newVal) -> {
            frame.getChoices().clear();
            for (String line : newVal.split("\n")) {
                if (line.contains(":")) {
                    var parts = line.split(":", 2);
                    var c = new DialogueFrameDefinition.DialogueChoiceDefinition();
                    c.setText(parts[0].trim());
                    c.setNextId(parts[1].trim());
                    frame.getChoices().add(c);
                }
            }
        });
        var choicesBox = new VBox(5, new Label("Choices:"), choicesArea);
        content.getChildren().add(choicesBox);

        // Visibility of choices depends on type
        choicesBox.visibleProperty().bind(typeCombo.valueProperty().isEqualTo("choice"));
        choicesBox.managedProperty().bind(choicesBox.visibleProperty());

        var removeBtn = new Button("Remove Frame");
        removeBtn.setOnAction(e -> {
            def.getFrames().remove(index);
            refreshFrames();
        });
        content.getChildren().add(removeBtn);

        return box;
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void save() {
    }
}
