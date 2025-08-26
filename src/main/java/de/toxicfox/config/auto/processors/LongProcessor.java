package de.toxicfox.config.auto.processors;

import de.toxicfox.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

public class LongProcessor implements JsonProcessor {
    @Override
    public JsonNode toJson(Object obj) {
        return JsonNode.number((long) obj);
    }

    @Override
    public Object fromJson(JsonNode node, Object obj, boolean optional) {
        if (node == null) {
            if (optional) {
                return obj;
            }
            throw new RuntimeException("Missing field");
        }

        return node.asLong();
    }
}
