package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
/**
 * 物品工具类 - 提供设置物品名称的实用方法
 */
public class ItemUtil {
    /**
     * 为已有物品设置显示名称
     * @param name   要设置的名称（Adventure文本组件）
     * @param stack  要修改的物品堆
     */
    public static void setName(Component name, ItemStack stack) {
        // 获取物品元数据
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return; // 安全校验
        // 设置显示名称
        meta.displayName(name);
        // 应用修改后的元数据
        stack.setItemMeta(meta);
    }
    /**
     * 创建新物品并设置名称（指定数量）
     * @param name   物品名称（Adventure文本组件）
     * @param item   物品材质
     * @param count  物品数量
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(Component name, Material item, int count) {
        // 创建基础物品堆
        ItemStack stack = new ItemStack(item, count);
        // 调用内部方法设置名称
        setName(name, stack);
        return stack;
    }
    /**
     * 创建新物品并设置名称（默认数量为1）
     * @param name   物品名称（Adventure文本组件）
     * @param item   物品材质
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(Component name, Material item) {
        // 创建基础物品堆（默认数量1）
        ItemStack stack = new ItemStack(item, 1);
        // 调用内部方法设置名称
        setName(name, stack);
        return stack;
    }
    /**
     * 创建新物品并设置名称（使用字符串名称，默认数量为1）
     * @param name   物品名称（字符串）
     * @param item   物品材质
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(String name, Material item) {
        // 将字符串转换为Adventure文本组件
        Component componentName = Component.text(name);
        // 创建基础物品堆（默认数量1）
        ItemStack stack = new ItemStack(item, 1);
        // 调用内部方法设置名称
        setName(componentName, stack);
        return stack;
    }
}
