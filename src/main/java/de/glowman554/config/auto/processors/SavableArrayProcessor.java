package de.glowman554.config.auto.processors;

import de.glowman554.config.Savable;
import de.glowman554.config.auto.JsonProcessor;
import net.shadew.json.JsonNode;

public class SavableArrayProcessor implements JsonProcessor {
    private final SavableConstructorReference base;
    private final SavableArrayConstructorReference array;

    public SavableArrayProcessor(SavableConstructorReference base, SavableArrayConstructorReference array) {
        this.base = base;
        this.array = array;
    }

    @Override
    public JsonNode toJson(Object obj) {
        JsonNode objectArray = JsonNode.array();

        for (Savable s : (Savable[]) obj) {
            objectArray.add(s.toJSON());
        }

        return objectArray;
    }

    @Override
    public Object fromJson(JsonNode node, Object obj, boolean optional) {
        if (node == null) {
            if (optional) {
                return obj;
            }
            throw new RuntimeException("Missing field");
        }

        Savable[] result = array.create(node.size());

        for (int i = 0; i < result.length; i++) {
            result[i] = base.create();
            result[i].fromJSON(node.get(i));
        }

        return result;
    }

    public interface SavableConstructorReference {
        Savable create();
    }

    public interface SavableArrayConstructorReference {
        Savable[] create(int size);
    }
}
