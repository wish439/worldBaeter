package org.wishtoday.wb.worldBaeter.Events.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.GUI.GUIManager;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class InventoryEvents implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickGlass(InventoryClickEvent e) {
        Component title = e.getView().title();
        if (!GUIManager.hasGUI(title)) {
            return;
        }
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        if (item.getType() != GuiUtils.DEFAULTERS) return;
        e.setCancelled(true);
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        GUIManager.handlerClose(e);
    }
}
