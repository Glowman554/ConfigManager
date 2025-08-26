package de.toxicfox.config.premade;

import de.toxicfox.config.Savable;
import de.toxicfox.config.auto.AutoSavable;
import de.toxicfox.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

import java.util.ArrayList;

public class ArrayListSavableOOP<T> extends ArrayList<T> implements Savable {
    private final JsonProcessor processor;

    public ArrayListSavableOOP(JsonProcessor processor) {
        if (processor == null) {
            throw new IllegalArgumentException("No valid JsonProcessor!");
        }
        this.processor = processor;
    }

    public ArrayListSavableOOP(Class<T> clazz) {
        this(AutoSavable.getProcessors().get(clazz));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fromJSON(JsonNode node) {
        clear();

        for (JsonNode t : node) {
            try {
                Class<? extends Savable> clazz = (Class<? extends Savable>) this.getClass().getClassLoader()
                        .loadClass(t.get("className").asString());

                add((T) processor.fromJson(t.get("data"), clazz.getDeclaredConstructor().newInstance(), false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JsonNode toJSON() {
        JsonNode array = JsonNode.array();

        for (T t : this) {
            JsonNode node = JsonNode.object();

            node.set("data", processor.toJson(t));
            node.set("className", t.getClass().getTypeName());

            array.add(node);
        }

        return array;
    }

    public static interface ConstructorReference<T> {
        T create();
    }
}
