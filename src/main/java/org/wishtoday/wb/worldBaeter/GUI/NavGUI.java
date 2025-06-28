package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class NavGUI {
    private static final int GUI_SIZE = 27;
    private static final Component GUI_TITLE = Component.text("World Baeter");
    private static Inventory inventory = GuiUtils.createInventoryWithGlass(GUI_SIZE, GUI_TITLE);

    public static int getGuiSize() {
        return GUI_SIZE;
    }

    public static void open(Player player) {
        player.closeInventory();
        player.openInventory(inventory);
    }

    public static Component getTitle() {
        return GUI_TITLE;
    }
    public static Inventory getInventory() {
        return inventory;
    }
}
