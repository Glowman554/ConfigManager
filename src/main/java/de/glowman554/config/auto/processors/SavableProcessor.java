package de.glowman554.config.auto.processors;

import de.glowman554.config.Savable;
import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

public class SavableProcessor implements JsonProcessor {
    @Override
    public JsonNode toJson(Object obj) {
        return ((Savable) obj).toJSON();
    }

    @Override
    public Object fromJson(JsonNode node, Object obj, boolean optional) {
        if (node == null) {
            if (optional) {
                return obj;
            }
            throw new RuntimeException("Missing field");
        }

        ((Savable) obj).fromJSON(node);

        return obj;
    }
}
