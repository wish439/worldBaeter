package org.wishtoday.wb.worldBaeter.Config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.wishtoday.wb.worldBaeter.GUI.EmailGUI;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Config {
    public static final File EMAIL_FILE = new File(WorldBaeter.getInstance().getDataFolder(), "email.yml");
    public static final File MARKET_FILE = new File(WorldBaeter.getInstance().getDataFolder(), "market.yml");
    public static final File CONFIG_FILE = new File(WorldBaeter.getInstance().getDataFolder(), "datas.yml");
    private static FileConfiguration defaultConfig = WorldBaeter.getInstance().getConfig();
    public static Integer max_sell_count = defaultConfig.getInt(ConfigPath.MAX_SELL_COUNT.s);

    public static void saveMarket(Map<ItemStack, MarketItemData> map) {
        YamlConfiguration marketConfig = new YamlConfiguration();
        for (Map.Entry<ItemStack, MarketItemData> entry : map.entrySet()) {
            String key = itemStackToBase64(entry.getKey());
            if (key != null) {
                marketConfig.set(key, entry.getValue()); // 要求 MarketItemData 实现 ConfigurationSerializable
            }
        }

        try {
            marketConfig.save(MARKET_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<ItemStack, MarketItemData> loadMarket() {
        Map<ItemStack, MarketItemData> map = new HashMap<>();
        if (!MARKET_FILE.exists()) return map;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(MARKET_FILE);
        for (String key : config.getKeys(false)) {
            ItemStack item = itemStackFromBase64(key);
            MarketItemData data = (MarketItemData) config.get(key);
            if (item != null && data != null) {
                map.put(item, data);
            }
        }
        return map;
    }
    public static void saveEmail() {
        YamlConfiguration emailConfig = new YamlConfiguration();
        for (Map.Entry<UUID, List<ItemStack>> entry : EmailGUI.emailItems.entrySet()) {
            UUID key = entry.getKey();
            emailConfig.set(key.toString(), entry.getValue());
        }
        try {
            emailConfig.save(EMAIL_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadEmail() {
        if (!EMAIL_FILE.exists()) return;
        YamlConfiguration loaded = YamlConfiguration.loadConfiguration(EMAIL_FILE);
        for (String s : loaded.getKeys(false)) {
            UUID uuid = UUID.fromString(s);
            List<ItemStack> list = (List<ItemStack>) loaded.getList(s);
            EmailGUI.emailItems.put(uuid, list);
        }
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
