package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;

import java.util.HashMap;
import java.util.Map;

public class NavGUI {
    private static Map<Integer, ItemStack> addItems = new HashMap<>();
    private static final Component GUI_TITLE = Component.text("World Baeter");
    private static Inventory inventory = GuiUtils.createInventoryWithGlass(GuiUtils.SMALLCHESTSIZE, GUI_TITLE);

    public static int getInventorySize() {
        return inventory.getSize();
    }

    public static void open(Player player) {
        player.closeInventory();
        setInventoryItemFromMap();
        player.openInventory(inventory);
    }

    public static Component getTitle() {
        return GUI_TITLE;
    }

    public static Inventory getInventory() {
        return inventory;
    }
    public static Map<Integer, ItemStack> getAddItems() {
        return addItems;
    }

    //可能需要在服务器开启时调用
    public static void initializeInventoryItem() {
        addItems.put(11, ItemUtil.setName("出售", Material.DIAMOND));
    }
    public static void setInventoryItemFromMap() {
        for (Map.Entry<Integer, ItemStack> entry : addItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }

}
