package dev.midnightcoder.cache.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DialogueFrameDefinition implements Serializable {
    private String id;
    private String type; // npc, player, choice, end, message, item
    private String speaker;
    private String text;
    private String next;
    private int itemId;
    private List<DialogueChoiceDefinition> choices = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSpeaker() { return speaker; }
    public void setSpeaker(String speaker) { this.speaker = speaker; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getNext() { return next; }
    public void setNext(String next) { this.next = next; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public List<DialogueChoiceDefinition> getChoices() { return choices; }
    public void setChoices(List<DialogueChoiceDefinition> choices) { this.choices = choices; }

    public static class DialogueChoiceDefinition implements Serializable {
        private String text;
        private String nextId;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getNextId() { return nextId; }
        public void setNextId(String nextId) { this.nextId = nextId; }
    }
}
