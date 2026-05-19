package dev.midnightcoder.cache.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DialogueDefinition implements Serializable {
    private final int id;
    private String dialogueId;
    private String startFrameId;
    private List<DialogueFrameDefinition> frames = new ArrayList<>();

    public DialogueDefinition(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public String getDialogueId() { return dialogueId; }
    public void setDialogueId(String dialogueId) { this.dialogueId = dialogueId; }
    public String getStartFrameId() { return startFrameId; }
    public void setStartFrameId(String startFrameId) { this.startFrameId = startFrameId; }
    public List<DialogueFrameDefinition> getFrames() { return frames; }
    public void setFrames(List<DialogueFrameDefinition> frames) { this.frames = frames; }
}
