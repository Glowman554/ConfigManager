package de.glowman554.config.premade;

import de.glowman554.config.Savable;
import de.glowman554.config.auto.AutoSavable;
import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

import java.util.HashSet;

public class HashSetSavable<T> extends HashSet<T> implements Savable {
    private final JsonProcessor processor;
    private final ConstructorReference<T> constructorReference;

    public HashSetSavable(JsonProcessor processor, ConstructorReference<T> constructorReference) {
        this.constructorReference = constructorReference;
        if (processor == null) {
            throw new IllegalArgumentException("No valid JsonProcessor!");
        }
        this.processor = processor;
    }

    public HashSetSavable(Class<T> clazz, ConstructorReference<T> constructorReference) {
        this(AutoSavable.getProcessors().get(clazz), constructorReference);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fromJSON(JsonNode node) {
        clear();

        for (JsonNode object : node) {
            add((T) processor.fromJson(object, constructorReference.create()));
        }
    }

    @Override
    public JsonNode toJSON() {
        JsonNode root = JsonNode.array();
        for (T object : this) {
            root.add(processor.toJson(object));
        }
        return root;
    }

    public static interface ConstructorReference<T> {
        T create();
    }
}
