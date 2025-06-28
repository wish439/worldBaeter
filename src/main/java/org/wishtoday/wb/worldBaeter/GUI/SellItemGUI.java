package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.PlayerData;

import java.util.HashMap;
import java.util.Map;

public class SellItemGUI {
    private static int[] glassSize = {
            4,
            4 + 9,
            4 + 9 + 9,
            4 + 9 + 9 + 9,
            4 + 9 + 9 + 9 + 9,
            4 + 9 + 9 + 9 + 9 + 9};
    public static final Component GUI_NAME = Component.text("Sell Item", NamedTextColor.GOLD);
    public static Map<PlayerData, Inventory> invs = new HashMap<>();

    public static void createInventoryToPlayer(Player player) {
        PlayerData data = new PlayerData(player);
        if (invs.containsKey(data)) return;
        invs.put(data, createInventory());
    }
    public static void openInventoryToPlayer(Player player) {
        player.closeInventory();
        PlayerData data = new PlayerData(player);
        if (!invs.containsKey(data)) return;
        Inventory inventory = invs.get(data);
        if (inventory == null) return;
        player.openInventory(inventory);
    }

    private static Inventory createInventory() {
        return GuiUtils.createInventoryWithGlass(GuiUtils.BIGCHESTSIZE, GUI_NAME, glassSize);
    }
}
