package org.wishtoday.wb.Impls;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public interface GUIInterface {
    void open(Player player);
    Component getTitle();
    Inventory getInventory();
    boolean isGUIItem(Component component);
    void runAction(Component name, Player player, ItemStack item,
                   ClickType clickType, InventoryAction inventoryAction
                    , int slot, InventoryClickEvent event);
}