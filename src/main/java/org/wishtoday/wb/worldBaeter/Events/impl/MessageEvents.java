package org.wishtoday.wb.worldBaeter.Events.impl;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.wishtoday.wb.worldBaeter.GUI.SellItemGUI;
import org.wishtoday.wb.worldBaeter.Util.ConfigUtils;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class MessageEvents implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!player.getPersistentDataContainer().has(SellItemGUI.IS_CLICKED, PersistentDataType.BOOLEAN))
            return;
        event.setCancelled(true);
        Component message = event.message();
        Material itemType = ConfigUtils.searchMap(message);
        SellItemGUI gui = SellItemGUI.GUI_MAP.get(player.getUniqueId());
        Integer i = player.getPersistentDataContainer().get(SellItemGUI.PLAYER_CLICK_SLOT, PersistentDataType.INTEGER);
        if (i == null) return;
        if (itemType != null) {
            gui.getInventory().setItem(i, new ItemStack(itemType));
        } else {
            player.sendMessage("未查询到对应物品,请核实后重新尝试");
        }
        GuiUtils.removePlayerTag(player,SellItemGUI.PLAYER_CLICK_SLOT);
        GuiUtils.removePlayerTag(player,SellItemGUI.IS_CLICKED);
        gui.open(player);
    }
}
