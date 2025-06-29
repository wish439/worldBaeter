package org.wishtoday.wb.Impls;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface ClickAction {
    void click(Player player
            , ItemStack item
            , ClickType clickType
            , InventoryAction action
            , int slot
            , InventoryClickEvent event
    );
}
