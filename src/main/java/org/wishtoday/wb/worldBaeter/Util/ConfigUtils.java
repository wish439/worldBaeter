package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.util.HashMap;
import java.util.Map;

public class ConfigUtils {
    public static Map<Component, Material> map = new HashMap<>();

    public static void initializeMap() {
        FileConfiguration config = WorldBaeter.getInstance().getConfig();
        ConfigurationSection id = config.getConfigurationSection("item_id");
        Map<String, Object> values = id.getValues(true);
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = entry.getKey();
            Material material = Material.getMaterial(key, false);
            if (material == null) continue;
            map.put(Component.text((String) entry.getValue()), material);
        }
    }

    @Nullable
    public static Material searchMap(Component search) {
        if (!map.containsKey(search)) {
            PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
            String serialize = serializer.serialize(search);
            return Material.getMaterial(serialize.toUpperCase(), false);
        }
        return map.get(search);
    }
}
