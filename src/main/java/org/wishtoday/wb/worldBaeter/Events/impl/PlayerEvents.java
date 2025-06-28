package org.wishtoday.wb.worldBaeter.Events.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;
import org.wishtoday.wb.worldBaeter.GUI.SellItemGUI;
import org.wishtoday.wb.worldBaeter.Util.PlayerData;

import java.util.ArrayList;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (MarketGUI.items.containsKey(new PlayerData(player))) return;
        MarketGUI.items.put(new PlayerData(player),new ArrayList<>());
        SellItemGUI.createInventoryToPlayer(player);
    }
}
