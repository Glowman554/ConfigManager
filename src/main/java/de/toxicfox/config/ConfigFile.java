package de.toxicfox.config;

import de.toxicfox.config.auto.AutoFileSavable;

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
