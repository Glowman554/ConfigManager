package de.glowman554.config;

import de.glowman554.config.auto.AutoFileSavable;
import de.glowman554.config.auto.AutoSavable;

import java.io.File;

public class ConfigFile extends AutoFileSavable {
    private final File configFile;

    public ConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public void save() {
        super.save(configFile);
    }

    public void load() {
        super.load(configFile);
    }
}
