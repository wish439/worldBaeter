package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    public static void setName(Component name, ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return;
        meta.displayName(name);
        stack.setItemMeta(meta);
    }

    public static ItemStack setName(Component name, Material item, int count) {
        ItemStack stack = new ItemStack(item, count);
        setName(name, stack);
        return stack;
    }
    public static ItemStack setName(Component name, Material item) {
        ItemStack stack = new ItemStack(item, 1);
        setName(name, stack);
        return stack;
    }
    public static ItemStack setName(String name, Material item) {
        ItemStack stack = new ItemStack(item, 1);
        setName(Component.text(name), stack);
        return stack;
    }
}
