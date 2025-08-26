package de.toxicfox.config.auto;

import net.shadew.json.JsonNode;

public interface JsonProcessor {
    JsonNode toJson(Object obj);

    Object fromJson(JsonNode node, Object obj, boolean optional);
}
