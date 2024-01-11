package de.glowman554.config.auto.processors;

import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

public class IntegerArrayProcessor implements JsonProcessor {
    @Override
    public JsonNode toJson(Object obj) {
        return JsonNode.numberArray((int[]) obj);
    }

    @Override
    public Object fromJson(JsonNode node, Object obj) {
        if (node != null) {
            return node.asIntArray();
        } else {
            return obj;
        }
    }
}
