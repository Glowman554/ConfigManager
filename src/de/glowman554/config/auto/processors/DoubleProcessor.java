package de.glowman554.config.auto.processors;

import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

public class DoubleProcessor implements JsonProcessor {
    @Override
    public JsonNode toJson(Object obj) {
        return JsonNode.number((double) obj);
    }

    @Override
    public Object fromJson(JsonNode node, Object obj) {
        if (node != null) {
            return node.asDouble();
        } else {
            return obj;
        }
    }
}
