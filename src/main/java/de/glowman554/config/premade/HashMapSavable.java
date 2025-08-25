package de.glowman554.config.premade;

import de.glowman554.config.Savable;
import de.glowman554.config.auto.AutoSavable;
import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

import java.util.HashMap;

public class HashMapSavable<T> extends HashMap<String, T> implements Savable {
    private final JsonProcessor processor;
    private final ConstructorReference<T> constructorReference;

    public HashMapSavable(JsonProcessor processor, ConstructorReference<T> constructorReference) {
        this.constructorReference = constructorReference;
        if (processor == null) {
            throw new IllegalArgumentException("No valid JsonProcessor!");
        }
        this.processor = processor;
    }

    public HashMapSavable(Class<T> clazz, ConstructorReference<T> constructorReference) {
        this(AutoSavable.getProcessors().get(clazz), constructorReference);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fromJSON(JsonNode node) {
        clear();

        for (String key : node.keySet()) {
            put(key, (T) processor.fromJson(node.get(key), constructorReference.create(), false));
        }
    }

    @Override
    public JsonNode toJSON() {
        JsonNode root = JsonNode.object();
        for (String key : keySet()) {
            root.set(key, processor.toJson(get(key)));
        }
        return root;
    }

    public static interface ConstructorReference<T> {
        T create();
    }
}
