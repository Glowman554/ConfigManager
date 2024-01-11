package de.glowman554.config.premade;

import de.glowman554.config.Savable;
import net.shadew.json.JsonNode;

public class StringSavable implements Savable {
    private String value;

    public StringSavable(String value) {
        this.value = value;
    }

    @Override
    public void fromJSON(JsonNode node) {
        value = node.asString();
    }

    @Override
    public JsonNode toJSON() {
        return JsonNode.string(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}