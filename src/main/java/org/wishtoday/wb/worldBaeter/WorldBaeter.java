package org.wishtoday.wb.worldBaeter;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.wishtoday.wb.worldBaeter.Command.MarketCommand;
import org.wishtoday.wb.worldBaeter.Events.impl.InventoryEvents;
import org.wishtoday.wb.worldBaeter.Events.impl.PlayerEvents;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;

import static org.wishtoday.wb.worldBaeter.Events.RegisterEvent.registerEvent;

public final class WorldBaeter extends JavaPlugin {
    private static WorldBaeter plugin;
    public WorldBaeter() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerEvent(this.getServer().getPluginManager(),this);
        NavGUI.initializeInventoryItem();
        registerCommands();
    }
    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,event -> {
                    Commands registrar = event.registrar();
                    MarketCommand.registerCommand(registrar);
                }
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static WorldBaeter getInstance() {
        return plugin;
    }
}
