package de.glowman554.config.auto;

import de.glowman554.config.Savable;
import de.glowman554.config.auto.processors.*;
import net.shadew.json.JsonNode;

import java.lang.reflect.Field;
import java.util.*;

public class AutoSavable implements Savable {
    private static final HashMap<Class<?>, JsonProcessor> processors = new HashMap<>();
    public static Logger debug = msg -> {
    };

    static {
        processors.put(int.class, new IntegerProcessor());
        processors.put(int[].class, new IntegerArrayProcessor());
        processors.put(long.class, new LongProcessor());
        processors.put(long[].class, new LongArrayProcessor());
        processors.put(double.class, new DoubleProcessor());
        processors.put(double[].class, new DoubleArrayProcessor());
        processors.put(boolean.class, new BooleanProcessor());
        processors.put(boolean[].class, new BooleanArrayProcessor());
        processors.put(String.class, new StringProcessor());
        processors.put(String[].class, new StringArrayProcessor());
        processors.put(Savable.class, new SavableProcessor());
    }


    @Override
    public void fromJSON(JsonNode node) {
        HashMap<Class<?>, JsonProcessor> localProcessors = loadLocalProcessors();

        for (Field field : getAllFields(this.getClass())) {
            if (field.isAnnotationPresent(Saved.class)) {
                JsonProcessor processor = getProcessor(localProcessors, field, field.getAnnotation(Saved.class));
                try {
                    field.setAccessible(true);
                    field.set(this, processor.fromJson(node.get(field.getName()), field.get(this)));
                    debug.debug("Loaded " + field.getName() + " using processor " + processor.getClass().getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public JsonNode toJSON() {
        JsonNode root = JsonNode.object();

        HashMap<Class<?>, JsonProcessor> localProcessors = loadLocalProcessors();

        for (Field field : getAllFields(this.getClass())) {
            if (field.isAnnotationPresent(Saved.class)) {
                JsonProcessor processor = getProcessor(localProcessors, field, field.getAnnotation(Saved.class));
                try {
                    field.setAccessible(true);
                    root.set(field.getName(), processor.toJson(field.get(this)));
                    debug.debug("Saved " + field.getName() + " using processor " + processor.getClass().getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return root;
    }

    private HashMap<Class<?>, JsonProcessor> loadLocalProcessors() {
        HashMap<Class<?>, JsonProcessor> localProcessors = new HashMap<>();
        for (Class<?> clazz : processors.keySet()) {
            localProcessors.put(clazz, processors.get(clazz));
        }

        for (Field field : getAllFields(this.getClass())) {
            if (field.isAnnotationPresent(Processor.class)) {
                Processor custom = field.getAnnotation(Processor.class);
                try {
                    field.setAccessible(true);
                    JsonProcessor processor = (JsonProcessor) field.get(this);
                    localProcessors.put(custom.target(), processor);
                    debug.debug("Found processor " + processor.getClass().getName() + " for " + custom.target().getName() + " in " + field.getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return localProcessors;
    }


    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            Collections.addAll(allFields, fields);
            clazz = clazz.getSuperclass();
        }

        return allFields;
    }

    private JsonProcessor getProcessor(HashMap<Class<?>, JsonProcessor> localProcessors, Field field, Saved annotation) {
        Class<?> clazz = field.getType();
        if (annotation.remap() != Object.class) {
            debug.debug("Remapping " + clazz.getName() + " to " + annotation.remap().getName() + " for field " + field.getName());
            clazz = annotation.remap();
        }
        JsonProcessor processor = localProcessors.get(clazz);
        if (processor == null) {
            throw new RuntimeException("Could not find JsonProcessor for field " + field.getName() + " with type " + clazz.getName());
        }
        return processor;
    }

    public interface Logger {
        void debug(String msg);
    }
}