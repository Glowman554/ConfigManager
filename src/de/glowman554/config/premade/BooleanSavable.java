package de.glowman554.config.premade;

import de.glowman554.config.Savable;
import net.shadew.json.JsonNode;

public class BooleanSavable implements Savable {
    private boolean value;

    public BooleanSavable(boolean value) {
        this.value = value;
    }

    @Override
    public void fromJSON(JsonNode node) {
        value = node.asBoolean();
    }

    @Override
    public JsonNode toJSON() {
        return JsonNode.bool(value);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}