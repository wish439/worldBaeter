package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.wishtoday.wb.worldBaeter.Util.MarketItems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketGUI {
    public static Map<String, List<MarketItems>> items = new HashMap<>();
    public static final int INVENTORY_SIZE = 54;
    public static final Component INVENTORY_NAME = Component.text("Market");
    private static Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_NAME);

    public static Inventory getInventory() {
        return inventory;
    }

    public static void openInventory(Player player) {
        player.closeInventory();
        createInventory();
        player.openInventory(inventory);
    }

    public static void addToItems(Player player, MarketItems item) {
        String name = player.getName();
        items.get(name).add(item);
    }

    public static void addToItems(Player player, ItemStack item, ItemStack needItem) {
        addToItems(player, new MarketItems(item, needItem));
    }

    public static Inventory createInventory() {
        inventory.clear();
        for (Map.Entry<String, List<MarketItems>> entry : items.entrySet()) {
            String s = entry.getKey();
            for (MarketItems marketItems : entry.getValue()) {
                ItemStack item = addLore(marketItems.getItem(), marketItems.getNeedItem(), s);
                inventory.addItem(item);
            }
        }
        return inventory;
    }

    private static ItemStack addLore(ItemStack item, ItemStack needItem, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta.lore() == null) {
            meta.lore(List.of(Component.text("此物品由" + name + "出售")
                    ,Component.text("他需要提交"
                            + needItem.getAmount()
                            + "个"
                            + needItem.getType().name()
                            + "来完成")));
            item.setItemMeta(meta);
            return item;
        }
        meta.lore().add(Component.text("此物品由" + name + "出售"));
        meta.lore().add(Component.text("他需要提交"
                + needItem.getAmount()
                + "个"
                + needItem.getType().name()
                + "来完成"));
        item.setItemMeta(meta);
        return item;
    }
}
