package org.wishtoday.wb.worldBaeter.Config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final File CONFIG_FILE = new File(WorldBaeter.getInstance().getDataFolder(), "datas.yml");
    private static YamlConfiguration config = new YamlConfiguration();

    /*public static void load() {
        if (!CONFIG_FILE.exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
            save();
        }
        try {
            config.load(CONFIG_FILE);
            System.out.println(config.getConfigurationSection("market_items"));
            loadDatas();
        } catch (IOException | InvalidConfigurationException e) {
            WorldBaeter.getInstance().getLogger().warning("Failed to load config file!" + e.getMessage());
        }
    }*/

    private static void loadDatas() {
        Map<ItemStack, MarketItemData> object = config.getObject("market_items", Map.class);
        if (object != null)
            MarketGUI.getInstance().setItems(object);
    }

    public static void save(Map<ItemStack, MarketItemData> map) {
        YamlConfiguration config = new YamlConfiguration();
        for (Map.Entry<ItemStack, MarketItemData> entry : map.entrySet()) {
            String key = itemStackToBase64(entry.getKey());
            if (key != null) {
                config.set(key, entry.getValue()); // 要求 MarketItemData 实现 ConfigurationSerializable
            }
        }
        try {
            config.save(new File(WorldBaeter.getInstance().getDataFolder(), "market.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<ItemStack, MarketItemData> loadItemStackMap() {
        Map<ItemStack, MarketItemData> map = new HashMap<>();
        File file = new File(WorldBaeter.getInstance().getDataFolder(), "market.yml");
        if (!file.exists()) return map;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            ItemStack item = itemStackFromBase64(key);
            MarketItemData data = (MarketItemData) config.get(key);
            if (item != null && data != null) {
                map.put(item, data);
            }
        }
        return map;
    }



    public static YamlConfiguration getConfig() {
        return config;
    }

    public static String itemStackToBase64(ItemStack item) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {

            dataOutput.writeObject(item);
            dataOutput.flush();

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ItemStack itemStackFromBase64(String base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {

            return (ItemStack) dataInput.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
