package org.wishtoday.wb.worldBaeter.Events.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.wishtoday.wb.worldBaeter.GUI.EmailGUI;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EmailGUI.createEmailToPlayer(player);
    }
}
