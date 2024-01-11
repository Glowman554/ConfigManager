package de.glowman554.config.auto.processors;

import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

public class LongArrayProcessor implements JsonProcessor {
    @Override
    public JsonNode toJson(Object obj) {
        return JsonNode.numberArray((long[]) obj);
    }

    @Override
    public Object fromJson(JsonNode node, Object obj) {
        if (node != null) {
            return node.asLongArray();
        } else {
            return obj;
        }
    }
}
