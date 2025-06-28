package org.wishtoday.wb.worldBaeter;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.wishtoday.wb.worldBaeter.Command.MarketCommand;
import org.wishtoday.wb.worldBaeter.Events.InventoryEvents;
import org.wishtoday.wb.worldBaeter.Events.PlayerEvents;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;

public final class WorldBaeter extends JavaPlugin {
    private static WorldBaeter plugin;
    public WorldBaeter() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerEvent(this.getServer().getPluginManager());
        NavGUI.initializeInventoryItem();
        registerCommands();
    }
    private void registerEvent(PluginManager pluginManager) {
        pluginManager.registerEvents(new PlayerEvents(), this);
        pluginManager.registerEvents(new InventoryEvents(), this);
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
