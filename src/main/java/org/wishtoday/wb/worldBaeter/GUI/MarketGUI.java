package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.wishtoday.wb.worldBaeter.Util.MarketItems;
import org.wishtoday.wb.worldBaeter.Util.PlayerData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 市场GUI类，用于展示玩家出售的物品交易信息
 */
public class MarketGUI {
    // 存储市场物品数据（Key: 玩家名称, Value: 该玩家出售的物品列表）
    public static Map<PlayerData, List<MarketItems>> items = new HashMap<>();
    // GUI常量设置
    public static final int INVENTORY_SIZE = 54;// 大箱子尺寸（6×9）
    public static final Component INVENTORY_NAME = Component.text("Market");
    // 创建基础GUI实例
    private static Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_NAME);
    // 获取市场GUI库存实例
    public static Inventory getInventory() {
        return inventory;
    }
    /**
     * 为玩家打开市场GUI
     * @param player 要打开GUI的玩家
     */
    public static void openInventory(Player player) {
        player.closeInventory();
        createInventory();
        player.openInventory(inventory);
    }
    /**
     * 添加MarketItems对象到市场
     * @param player 出售物品的玩家
     * @param item   要添加的市场物品对象
     */
    public static void addToItems(Player player, MarketItems item) {
        String name = player.getName();
        // 将物品添加到对应玩家的出售列表
        items.get(name).add(item);
    }
    /**
     * 添加物品到市场的重载方法（直接通过物品创建MarketItems对象）
     * @param player    出售物品的玩家
     * @param item      出售的物品
     * @param needItem  交易所需的物品
     */
    public static void addToItems(Player player, ItemStack item, ItemStack needItem) {
        // 创建MarketItems对象并添加到市场
        addToItems(player, new MarketItems(item, needItem));
    }
    /**
     * 创建/更新市场GUI内容
     * @return 更新后的库存
     */
    public static Inventory createInventory() {
        inventory.clear();
        // 遍历所有玩家及其出售物品
        for (Map.Entry<PlayerData, List<MarketItems>> entry : items.entrySet()) {
            PlayerData data = entry.getKey();
            // 遍历该卖家的所有出售物品
            for (MarketItems marketItems : entry.getValue()) {
                // 为物品添加描述信息并放入GUI
                ItemStack item = addLore(marketItems.getItem(), marketItems.getNeedItem(), data);
                inventory.addItem(item);// 添加到GUI
            }
        }
        return inventory;
    }
    /**
     * 为市场物品添加描述信息（Lore）
     * @param item     要添加描述的物品
     * @param needItem 交易所需的物品
     * @param data     玩家数据
     * @return 添加描述后的物品
     */
    private static ItemStack addLore(ItemStack item
            , ItemStack needItem
            , PlayerData data) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;// 安全性检查
        // 处理Lore（分两种情况：已有Lore和没有Lore）
        if (meta.lore() == null) {
            // 没有Lore时直接设置新Lore
            meta.lore(List.of(Component.text("此物品由" + data.getName() + "出售")// 创建描述文本
                    ,Component.text("他需要提交"
                            + needItem.getAmount()
                            + "个"
                            + needItem.getType().name()
                            + "来完成")));
            item.setItemMeta(meta);
            return item;
        }
        meta.lore().add(Component.text("此物品由" + data.getName() + "出售"));
        meta.lore().add(Component.text("他需要提交"
                + needItem.getAmount()
                + "个"
                + needItem.getType().name()
                + "来完成"));
        item.setItemMeta(meta);// 应用修改后的ItemMeta
        return item;
    }
}
