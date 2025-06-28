package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;
/**
 * GUI工具类 - 提供创建和管理GUI的实用方法
 */
public class GuiUtils {
    // 标准GUI尺寸常量
    public static final int SMALLCHESTSIZE = 27; // 小箱子尺寸（3×9）
    public static final int BIGCHESTSIZE = SMALLCHESTSIZE * 2;  // 大箱子尺寸（6×9）
    /**
     * 创建带玻璃背景的GUI
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
     * 设置库存的玻璃背景（自定义玻璃物品）
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
            glassItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        // 填充整个库存的每个槽位
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, glassItem.clone()); // 使用克隆避免物品引用共享
        }
        return inventory;
    }
    /**
     * 设置库存的玻璃背景（使用默认玻璃）
     * @param inventory 要设置的库存
     * @return 设置完成的库存（如果输入无效则返回null）
     */
    @Nullable
    public static Inventory setGlass(Inventory inventory) {
        return setGlass(inventory, null);
    }
    /**
     * 判断是否需要取消GUI事件（基于标题检查）
     *
     * 用于事件处理中判断点击的GUI是否属于本插件
     * @param title 要检查的GUI标题
     * @return true - 需要取消事件（非本插件的GUI）
     *         false - 属于本插件的GUI（导航菜单或市场GUI）
     */
    public static boolean isNeedCancelGUI(Component title) {
        // 检查标题是否既不是导航GUI也不是市场GUI
        return !title.equals(NavGUI.getTitle())
                && !title.equals(MarketGUI.INVENTORY_NAME);
    }
}
