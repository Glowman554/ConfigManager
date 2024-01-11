package de.glowman554.config.premade;

import de.glowman554.config.Savable;
import net.shadew.json.JsonNode;

public class IntegerSavable implements Savable {
    private int value;

    public IntegerSavable(int value) {
        this.value = value;
    }

    @Override
    public void fromJSON(JsonNode node) {
        value = node.asInt();
    }

    @Override
    public JsonNode toJSON() {
        return JsonNode.number(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}