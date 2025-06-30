package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.wishtoday.wb.Impls.ClickAction;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;

import java.util.Arrays;
import java.util.Map;

/**
 * GUI工具类 - 提供创建和管理GUI的实用方法
 */
public class GuiUtils {
    // 标准GUI尺寸常量
    public static final Material DEFAULTERS = Material.GRAY_STAINED_GLASS_PANE;
    public static final int SMALLCHESTSIZE = 27; // 小箱子尺寸（3×9）
    public static final int BIGCHESTSIZE = SMALLCHESTSIZE * 2;  // 大箱子尺寸（6×9）

    /**
     * 创建带玻璃背景的GUI
     *
     * @param size  库存大小
     * @param title GUI标题
     * @return 创建并设置好玻璃背景的Inventory对象
     */
    public static Inventory createInventoryWithGlass(int size, Component title) {
        // 1. 创建基础库存
        Inventory inventory = Bukkit.createInventory(null, size, title);
        // 2. 设置玻璃背景并返回
        return setGlass(inventory);
    }

    /**
     * 创建自定义玻璃位置的GUI
     *
     * @param size  库存大小
     * @param title GUI标题
     * @param sizes 需要填充玻璃的槽位
     * @return 创建并设置好玻璃背景的Inventory对象
     */
    public static Inventory createInventoryWithGlass(Component title, int size, int... sizes) {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        return customSetGlass(inventory, sizes);
    }

    public static Inventory customSetGlass(Inventory inventory
            , int... sizes) {
        for (int size : sizes) {
            inventory.setItem(size, new ItemStack(DEFAULTERS));
        }
        return inventory;
    }

    /**
     * 设置库存的玻璃背景（自定义玻璃物品）
     *
     * @param inventory 要设置的库存
     * @param glassItem 用作背景的玻璃物品
     * @return 设置完成的库存（如果输入无效则返回null）
     */
    @Nullable
    public static Inventory setGlass(Inventory inventory, ItemStack glassItem) {
        // 安全检查
        if (inventory == null) return null;
        int size = inventory.getSize();
        if (size == 0) return null;
        // 使用默认玻璃物品（灰色染色玻璃板）如果未提供
        if (glassItem == null)
            glassItem = new ItemStack(DEFAULTERS, 1);
        // 填充整个库存的每个槽位
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, glassItem.clone()); // 使用克隆避免物品引用共享
        }
        return inventory;
    }

    /**
     * 设置库存的玻璃背景（使用默认玻璃）
     *
     * @param inventory 要设置的库存
     * @return 设置完成的库存（如果输入无效则返回null）
     */
    @Nullable
    public static Inventory setGlass(Inventory inventory) {
        return setGlass(inventory, null);
    }

    public static void removePlayerTag(Player player, NamespacedKey namespacedKey) {
        player.getPersistentDataContainer().remove(namespacedKey);
    }
    public static boolean isFull(Inventory inventory, int slot) {
        @Nullable ItemStack[] contents = inventory.getContents();
        int i = 0;
        for (ItemStack content : contents) {
            if (content == null) continue;
            i++;
        }
        //int length = contents.length;
        return i == slot;
    }
    public static Inventory cloneInventory(Inventory inventory) {
        Inventory itemStacks = Bukkit.createInventory(null, inventory.getSize(), MarketGUI.title);
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) continue;
            itemStacks.setItem(i, item);
        }
        return itemStacks;
    }

}
