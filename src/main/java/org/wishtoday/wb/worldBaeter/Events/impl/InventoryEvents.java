package org.wishtoday.wb.worldBaeter.Events.impl;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class InventoryEvents implements Listener {
    @EventHandler
    public void onClickGlass(InventoryClickEvent e) {
        Component title = e.getView().title();
        if (GuiUtils.isNeedCancelGUI(title)) {
            return;
        }
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        if (item.getType() != Material.GRAY_STAINED_GLASS_PANE) return;
        e.setCancelled(true);
    }
}
