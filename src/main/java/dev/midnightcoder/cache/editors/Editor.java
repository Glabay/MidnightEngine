package dev.midnightcoder.cache.editors;

import javafx.scene.Node;

public interface Editor {
    Node getView();

    void save();
}
