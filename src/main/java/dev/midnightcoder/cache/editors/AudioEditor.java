package dev.midnightcoder.cache.editors;

import dev.midnightcoder.cache.CacheManager;
import dev.midnightcoder.cache.model.AudioDefinition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class AudioEditor implements Editor {
    private static final Logger log = LoggerFactory.getLogger(AudioEditor.class);
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox root = new VBox(10);
    private final AudioDefinition def;
    private final CacheManager cacheManager;
    private Clip clip;

    public AudioEditor(AudioDefinition def, CacheManager cacheManager) {
        this.def = def;
        this.cacheManager = cacheManager;
        root.setPadding(new Insets(10));
        scrollPane.setContent(root);
        scrollPane.setFitToWidth(true);
        refresh();
    }

    private void refresh() {
        root.getChildren().clear();

        root.getChildren().add(new Label("Editing Audio ID: " + def.getId()));

        var nameField = new TextField(def.getName());
            nameField.setPromptText("Audio Name (Identifier)");
            nameField.textProperty().addListener((obs, old, newVal) -> def.setName(newVal));
        root.getChildren().add(new HBox(10, new Label("Name:"), nameField));

        var playBtn = new Button("Play");
            playBtn.setDisable(def.getData() == null || def.getData().length == 0);
            playBtn.setOnAction(_ -> playAudio());

        var stopBtn = new Button("Stop");
            stopBtn.setDisable(true);
            stopBtn.setOnAction(_ -> {
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                }
            });

        root.getChildren().add(new HBox(10, playBtn, stopBtn));

        var infoGrid = new GridPane();
            infoGrid.setHgap(10);
            infoGrid.setVgap(10);
            infoGrid.add(new Label("Compressed Size:"), 0, 0);
            infoGrid.add(new Label(def.getCompressedSize() + " bytes"), 1, 0);
            infoGrid.add(new Label("Duration:"), 0, 1);
            infoGrid.add(new Label(String.format("%.2f seconds", def.getDuration())), 1, 1);
        root.getChildren().add(infoGrid);

        var importBtn = new Button("Import WAV");
            importBtn.setOnAction(_ -> importWav());

        root.getChildren().add(importBtn);
        
        playBtn.pressedProperty().addListener((_, _, _) -> {
           if (clip != null) stopBtn.setDisable(!clip.isRunning());
        });
    }

    private void playAudio() {
        if (def.getData() == null || def.getData().length == 0) return;

        try {
            if (clip != null && clip.isRunning())
                clip.stop();

            // Decompress data
            byte[] decompressed = decompress(def.getData());
            
            var ais = AudioSystem.getAudioInputStream(new ByteArrayInputStream(decompressed));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        }
        catch (Exception e) {
            log.error("Failed to play audio: {}", e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Failed to play audio: " + e.getMessage())
                .showAndWait();
        }
    }

    private void importWav() {
        var fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("WAV files", "*.wav"));
        var file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            try {
                // Get duration
                var ais = AudioSystem.getAudioInputStream(file);
                var format = ais.getFormat();
                var frames = ais.getFrameLength();
                var durationInSeconds = (frames + 0.0) / format.getFrameRate();
                ais.close();
                // Read all bytes
                byte[] originalData = java.nio.file.Files.readAllBytes(file.toPath());
                // Compress
                byte[] compressedData = compress(originalData);

                def.setName(file.getName().replace(".wav", ""));
                def.setData(compressedData);
                def.setCompressedSize(compressedData.length);
                def.setDuration(durationInSeconds);

                refresh();
            } catch (Exception e) {
                log.error("Failed to import WAV: {}", e.getMessage());
                new Alert(Alert.AlertType.ERROR, "Failed to import WAV: " + e.getMessage())
                    .showAndWait();
            }
        }
    }

    private byte[] compress(byte[] data) throws IOException {
        var baos = new ByteArrayOutputStream();
        try (var gzos = new GZIPOutputStream(baos)) {
            gzos.write(data);
        }
        return baos.toByteArray();
    }

    private byte[] decompress(byte[] compressedData) throws IOException {
        var bais = new ByteArrayInputStream(compressedData);
        try (var gzis = new GZIPInputStream(bais)) {
            return gzis.readAllBytes();
        }
    }

    @Override
    public Node getView() {
        return scrollPane;
    }

    @Override
    public void save() {}
}
