package org.wishtoday.wb.worldBaeter.Events;

import org.bukkit.plugin.PluginManager;
import org.wishtoday.wb.worldBaeter.Events.impl.GUI.NavGUIEvents;
import org.wishtoday.wb.worldBaeter.Events.impl.InventoryEvents;
import org.wishtoday.wb.worldBaeter.Events.impl.PlayerEvents;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

public class RegisterEvent {
    public static void registerEvent(PluginManager pluginManager
            , WorldBaeter plugin) {
        pluginManager.registerEvents(new PlayerEvents(), plugin);
        pluginManager.registerEvents(new InventoryEvents(), plugin);
        pluginManager.registerEvents(new NavGUIEvents(), plugin);
    }
}
