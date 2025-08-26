package de.toxicfox.config.auto.processors;

import de.toxicfox.config.Savable;
import de.toxicfox.config.auto.JsonProcessor;
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
