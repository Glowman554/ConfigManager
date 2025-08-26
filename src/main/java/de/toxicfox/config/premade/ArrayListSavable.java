package de.toxicfox.config.premade;

import de.toxicfox.config.Savable;
import de.toxicfox.config.auto.AutoSavable;
import de.toxicfox.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

import java.util.ArrayList;

public class ArrayListSavable<T> extends ArrayList<T> implements Savable {
    private final JsonProcessor processor;
    private final ConstructorReference<T> constructorReference;

    public ArrayListSavable(JsonProcessor processor, ConstructorReference<T> constructorReference) {
        this.constructorReference = constructorReference;
        if (processor == null) {
            throw new IllegalArgumentException("No valid JsonProcessor!");
        }
        this.processor = processor;
    }

    public ArrayListSavable(Class<T> clazz, ConstructorReference<T> constructorReference) {
        this(AutoSavable.getProcessors().get(clazz), constructorReference);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fromJSON(JsonNode node) {
        clear();
        for (JsonNode t : node) {
            add((T) processor.fromJson(t, constructorReference.create(), false));
        }
    }

    @Override
    public JsonNode toJSON() {
        JsonNode array = JsonNode.array();

        for (T t : this) {
            array.add(processor.toJson(t));
        }

        return array;
    }

    public static interface ConstructorReference<T> {
        T create();
    }
}
