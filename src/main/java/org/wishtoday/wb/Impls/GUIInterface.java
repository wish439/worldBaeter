package org.wishtoday.wb.Impls;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


public interface GUIInterface {
    void open(Player player);
    void open(Player player, int index);
    Component getTitle();
    Inventory getInventory();
    boolean isGUIItem(UUID component);
    void runAction(UUID uuid, Player player, ItemStack item,
                   ClickType clickType, InventoryAction inventoryAction
                    , int slot, InventoryClickEvent event);
    void onClose(InventoryCloseEvent event);
    void onClick(InventoryClickEvent event);
}