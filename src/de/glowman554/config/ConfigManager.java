package de.glowman554.config;

import net.shadew.json.Json;
import net.shadew.json.JsonNode;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    public static File BASE_FOLDER = new File("config");
    private final File FILE;
    private JsonNode rootNode;

    public ConfigManager(String id, boolean failIfNotFound) {
        FILE = new File(BASE_FOLDER, id + ".json");

        if (FILE.exists()) {
            load();
        } else {
            if (failIfNotFound) {
                throw new IllegalArgumentException("No config with id " + id + " found!");
            }
            rootNode = JsonNode.object();
        }
        save();
    }


    private void load() {
        Json json = Json.json();
        try {
            rootNode = json.parse(FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() {
        Json json = Json.json();
        try {
            json.serialize(rootNode, FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Savable loadValue(String id, Savable structure) {
        JsonNode node = rootNode.get(id);
        if (node == null) {
            throw new IllegalArgumentException("No value with id " + id);
        }
        structure.fromJSON(node);
        return structure;
    }

    public void setValue(String id, Savable structure) {
        rootNode.set(id, structure.toJSON());
        save();
    }
}