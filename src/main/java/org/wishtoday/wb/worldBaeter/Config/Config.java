package org.wishtoday.wb.worldBaeter.Config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.io.File;
import java.io.IOException;

public class Config {
    public static final File CONFIG_FILE = new File(WorldBaeter.getInstance().getDataFolder(), "datas.yml");
    private static YamlConfiguration config = new YamlConfiguration();

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
            save();
        }
        try {
            config.load(CONFIG_FILE);
        } catch (IOException | InvalidConfigurationException e) {
            WorldBaeter.getInstance().getLogger().warning("Failed to load config file!");
        }
    }

    public static void save() {
        try {
            config.save(CONFIG_FILE);
        } catch (IOException e) {
            WorldBaeter.getInstance().getLogger().warning("Failed to save config file!");
        }
    }
}
