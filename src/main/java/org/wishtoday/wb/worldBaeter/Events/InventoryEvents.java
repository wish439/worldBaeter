package org.wishtoday.wb.worldBaeter.Events;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;

public class InventoryEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        boolean b = e.getView().title().contains(MarketGUI.INVENTORY_NAME, Component.EQUALS);
        if (!b) return;
    }
}
