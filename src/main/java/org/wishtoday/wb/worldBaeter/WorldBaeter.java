package org.wishtoday.wb.worldBaeter;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.wishtoday.wb.worldBaeter.Command.MarketCommand;
import org.wishtoday.wb.worldBaeter.Config.Config;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;
import org.wishtoday.wb.worldBaeter.Util.ConfigUtils;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;
import org.wishtoday.wb.worldBaeter.Util.PlayerData;

import java.util.Map;

import static org.wishtoday.wb.worldBaeter.Events.RegisterEvent.registerEvent;
/**
 * WorldBaeter 插件主类 - 负责插件生命周期管理
 */
public final class WorldBaeter extends JavaPlugin {
    // 插件单例实例
    private static WorldBaeter plugin;
    // 构造器初始化插件实例
    public WorldBaeter() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // ========== 插件启动逻辑 ========== //
        plugin = this;
        this.saveDefaultConfig();
        ConfigUtils.initializeMap();// 确保数据文件夹存在
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        // 1. 注册所有事件监听器
        registerEvent(this.getServer().getPluginManager(),this);
        // 3. 注册插件命令
        registerCommands();
        registerConfigClass();
    }
    private void registerConfigClass() {
        ConfigurationSerialization.registerClass(MarketItemData.class);
        ConfigurationSerialization.registerClass(PlayerData.class);
    }
    /**
     * 注册插件命令（使用Paper的Brigadier命令系统）
     */
    private void registerCommands() {
        // 注册到Paper的命令生命周期事件
        this.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS, // 命令注册事件
                event -> {
                    // 获取命令注册器
                    Commands registrar = event.registrar();
                    // 注册市场相关命令
                    MarketCommand.registerCommand(registrar);
                }
        );
    }

    @Override
    public void onDisable() {
        // ========== 插件关闭逻辑 ========== //
        // 此处可添加数据保存、资源释放等操作
        //Config.save();
    }
    /**
     * 获取插件单例实例
     * @return WorldBaeter插件实例
     */
    public static WorldBaeter getInstance() {
        return plugin;
    }
}
