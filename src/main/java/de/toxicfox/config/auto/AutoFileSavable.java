package de.toxicfox.config.auto;

import net.shadew.json.Json;

import java.io.File;
import java.io.IOException;

public class AutoFileSavable extends AutoSavable {
    private boolean saveAfterLoad = false;

    public void save(File file) {
        try {
            Json.json().serialize(toJSON(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(File file) {
        try {
            fromJSON(Json.json().parse(file));
        } catch (IOException ignored) {

        }

        if (saveAfterLoad) {
            save(file);
        }
    }

    public void setSaveAfterLoad(boolean saveAfterLoad) {
        this.saveAfterLoad = saveAfterLoad;
    }
}
