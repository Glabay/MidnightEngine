package dev.midnightcoder.cache;

import dev.midnightcoder.cache.editors.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private final CacheManager cacheManager = new CacheManager();

    private TreeView<String> treeView;
    private BorderPane editorContainer;

    @Override
    public void start(Stage primaryStage) {
        cacheManager.load();
        primaryStage.setTitle("MidnightCache Editor");

        // TreeView on the left
        treeView = new TreeView<>();
        treeView.setCellFactory(_ -> new TreeCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setContextMenu(null);
                }
                else {
                    setText(item);
                    var treeItem = getTreeItem();
                    if (treeItem != null && treeItem.getParent() != null && treeItem.getParent().getValue().equals("MidnightCache")) {
                        var menu = new ContextMenu();
                        var addEntry = new MenuItem("Add New " + item.replace("_index", ""));
                            addEntry.setOnAction(_ -> addNewEntry(item));
                        menu.getItems().add(addEntry);
                        setContextMenu(menu);
                    }
                    else setContextMenu(null);
                }
            }
        });
        refreshTree();
        
        treeView.getSelectionModel().selectedItemProperty()
            .addListener((_, _, newVal) -> {
                if (newVal != null) handleSelection(newVal);
            });
        
        editorContainer = new BorderPane();
        editorContainer.setCenter(new Label("Select an item from the tree to edit"));
        
        var splitPane = new SplitPane();
            splitPane.getItems().addAll(treeView, editorContainer);
            splitPane.setDividerPositions(0.3);

        // Menu bar for adding items
        var addSprite = new MenuItem("Sprite");
            addSprite.setOnAction(_ -> addNewEntry("sprite_index"));
        
        var addItem = new MenuItem("Item");
            addItem.setOnAction(_ -> addNewEntry("item_index"));

        var addNpc = new MenuItem("NPC");
            addNpc.setOnAction(_ -> addNewEntry("npc_index"));

        var addObject = new MenuItem("GameObject");
            addObject.setOnAction(_ -> addNewEntry("gameobject_index"));

        var addMap = new MenuItem("Map");
            addMap.setOnAction(_ -> addNewEntry("map_index"));

        var addTexture = new MenuItem("Texture");
            addTexture.setOnAction(_ -> addNewEntry("texture_index"));
        
        var addSpriteSheet = new MenuItem("SpriteSheet");
            addSpriteSheet.setOnAction(_ -> addNewEntry("spritesheet_index"));

        var addAudio = new MenuItem("Audio");
            addAudio.setOnAction(_ -> addNewEntry("audio_index"));

        var addDialogue = new MenuItem("Dialogue");
            addDialogue.setOnAction(_ -> addNewEntry("dialogue_index"));

        var addMenu = new Menu("Add New");
            addMenu.getItems().addAll(addSprite, addSpriteSheet, addTexture, addItem, addNpc, addObject, addMap, addAudio, addDialogue);

        var saveItem = new MenuItem("Save Cache");
            saveItem.setOnAction(_ -> cacheManager.save());

        var fileMenu = new Menu("File");
            fileMenu.getItems().add(saveItem);

        var menuBar = new MenuBar();
            menuBar.getMenus().addAll(fileMenu, addMenu);

        var root = new BorderPane();
            root.setCenter(splitPane);
            root.setTop(menuBar);

        var scene = new Scene(root, 1200, 800);
        EditorSettings.applyTheme(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshTree() {
        var rootItem = new TreeItem<>("MidnightCache");
            rootItem.setExpanded(true);
            rootItem.getChildren().add(createIndexNode("texture_index", cacheManager.getTextures().size()));
            rootItem.getChildren().add(createIndexNode("item_index", cacheManager.getItems().size()));
            rootItem.getChildren().add(createIndexNode("npc_index", cacheManager.getNpcs().size()));
            rootItem.getChildren().add(createIndexNode("gameobject_index", cacheManager.getObjects().size()));
            rootItem.getChildren().add(createIndexNode("map_index", cacheManager.getMaps().size()));
            rootItem.getChildren().add(createIndexNode("audio_index", cacheManager.getAudio().size()));
            rootItem.getChildren().add(createIndexNode("dialogue_index", cacheManager.getDialogues().size()));
            rootItem.getChildren().add(createIndexNode("sprite_index", cacheManager.getSprites().size()));
            rootItem.getChildren().add(createIndexNode("spritesheet_index", cacheManager.getSpriteSheets().size()));
        treeView.setRoot(rootItem);
    }

    private TreeItem<String> createIndexNode(String name, int count) {
        var node = new TreeItem<>(name);
        for (int i = 0; i < count; i++) {
            node.getChildren().add(new TreeItem<>(name.split("_")[0] + " " + i));
        }
        return node;
    }

    private void handleSelection(TreeItem<String> item) {
        var value = item.getValue();
        var parent = item.getParent();
        
        // Root selected
        if (parent == null) {
            editorContainer.setCenter(new Label("MidnightCache Root"));
            return;
        }

        // Index category selected
        if (parent.getValue().equals("MidnightCache")) {
            showIndexDashboard(value);
            return;
        }

        var index = parent.getChildren().indexOf(item);
        var category = parent.getValue();

        switch (category) {
            case "sprite_index" -> showSpriteEditor(index);
            case "item_index" -> showItemEditor(index);
            case "npc_index" -> showNpcEditor(index);
            case "gameobject_index" -> showObjectEditor(index);
            case "texture_index" -> showTextureEditor(index);
            case "spritesheet_index" -> showSpriteSheetEditor(index);
            case "map_index" -> showMapEditor(index);
            case "audio_index" -> showAudioEditor(index);
            case "dialogue_index" -> showDialogueEditor(index);
        }
    }

    private void showIndexDashboard(String category) {
        var dash = new VBox(20);
            dash.setAlignment(Pos.CENTER);
        var title = new Label("Index: " + category);
            title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        var addButton = new Button("Add New " + category.replace("_index", ""));
            addButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20 10 20;");
            addButton.setOnAction(_ -> addNewEntry(category));

        dash.getChildren().addAll(title, addButton);
        editorContainer.setCenter(dash);
    }

    private TreeItem<String> findCategoryNode(String category) {
        return treeView.getRoot().getChildren()
            .stream()
            .filter(node ->
                node.getValue().equals(category))
            .findFirst()
            .orElse(null);
    }

    private void addNewEntry(String category) {
        switch (category) {
            case "sprite_index" -> addNewSprite();
            case "item_index" -> cacheManager.addItem();
            case "npc_index" -> cacheManager.addNpc();
            case "gameobject_index" -> cacheManager.addObject();
            case "texture_index" -> cacheManager.addTexture(-1, "#FFFFFF");
            case "spritesheet_index" -> cacheManager.addSpriteSheet(-1, 1, 1, 32, 32);
            case "map_index" -> addNewMap();
            case "audio_index" -> cacheManager.addAudio("new_audio", new byte[0], 0, 0);
            case "dialogue_index" -> cacheManager.addDialogue();
        }
        refreshTree();
        // Select the newly added item
        var categoryNode = findCategoryNode(category);
        if (categoryNode != null) {
            categoryNode.setExpanded(true);
            if (!categoryNode.getChildren().isEmpty()) {
                treeView.getSelectionModel().select(categoryNode.getChildren().getLast());
            }
        }
    }

    private void showSpriteEditor(int index) {
        editorContainer.setCenter(new SpriteEditor(cacheManager.getSprites().get(index)).getView());
    }

    private void showItemEditor(int index) {
        editorContainer.setCenter(new ItemEditor(cacheManager.getItems().get(index), cacheManager).getView());
    }

    private void showNpcEditor(int index) {
        editorContainer.setCenter(new NPCEditor(cacheManager.getNpcs().get(index), cacheManager).getView());
    }

    private void showObjectEditor(int index) {
        editorContainer.setCenter(new ObjectEditor(cacheManager.getObjects().get(index), cacheManager).getView());
    }

    private void showTextureEditor(int index) {
        editorContainer.setCenter(new TextureEditor(cacheManager.getTextures().get(index), cacheManager).getView());
    }

    private void showSpriteSheetEditor(int index) {
        editorContainer.setCenter(new SpriteSheetEditor(cacheManager.getSpriteSheets().get(index), cacheManager).getView());
    }

    private void showMapEditor(int index) {
        editorContainer.setCenter(new MapEditor(cacheManager.getMaps().get(index)).getView());
    }

    private void showAudioEditor(int index) {
        editorContainer.setCenter(new AudioEditor(cacheManager.getAudio().get(index), cacheManager).getView());
    }

    private void showDialogueEditor(int index) {
        editorContainer.setCenter(new DialogueEditor(cacheManager.getDialogues().get(index), cacheManager).getView());
    }

    private void addNewSprite() {
        cacheManager.addSprite(new byte[0], 0, 0);
    }
    
    private void addNewMap() {
        cacheManager.addMap(new byte[0]);
    }

    static void main(String[] args) {
        launch(args);
    }
}
