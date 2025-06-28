package org.wishtoday.wb.worldBaeter.Events.impl.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class NavGUIEvents implements Listener {
    @EventHandler
    public void onClickItem(final InventoryClickEvent e) {
        Component title = e.getView().title();
        if (GuiUtils.isNeedCancelGUI(title)) return;
        ItemStack item = e.getCurrentItem();
        if (item == null) return;
        Component component = item.getItemMeta().displayName();
        if (!NavGUI.isGUIItem(component)) return;
        e.setCancelled(true);
        HumanEntity humanEntity = e.getWhoClicked();
        if (!(humanEntity instanceof Player player)) return;
        NavGUI.runAction(component, player, item,e.getClick(),e.getAction());
    }
}
