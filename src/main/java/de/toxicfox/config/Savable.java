package de.toxicfox.config;


import net.shadew.json.JsonNode;

public interface Savable {
    void fromJSON(JsonNode node);

    JsonNode toJSON();
}