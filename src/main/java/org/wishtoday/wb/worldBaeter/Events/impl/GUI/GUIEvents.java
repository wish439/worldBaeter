package org.wishtoday.wb.worldBaeter.Events.impl.GUI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.wishtoday.wb.worldBaeter.GUI.GUIManager;

public class GUIEvents implements Listener {
    @EventHandler
    public void onClickItem(final InventoryClickEvent e) {
        GUIManager.handlerActions(e);
    }
}
