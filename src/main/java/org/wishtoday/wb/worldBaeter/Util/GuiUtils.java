package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GuiUtils {
    public static final int SMALLCHESTSIZE = 27;
    public static final int BIGCHESTSIZE = SMALLCHESTSIZE * 2;
    public static Inventory createInventoryWithGlass(int size, Component title) {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        return setGlass(inventory);
    }

    @Nullable
    public static Inventory setGlass(Inventory inventory
            , ItemStack glassItem) {
        if (inventory == null) return null;
        int size = inventory.getSize();
        if (size == 0) return null;
        if (glassItem == null)
            glassItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, glassItem);
        }
        return inventory;
    }

    @Nullable
    public static Inventory setGlass(Inventory inventory) {
        return setGlass(inventory, null);
    }
}
