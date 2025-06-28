package org.wishtoday.wb.worldBaeter;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.wishtoday.wb.worldBaeter.Command.MarketCommand;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;
import org.wishtoday.wb.worldBaeter.GUI.SellItemGUI;

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
        // 1. 注册所有事件监听器
        registerEvent(this.getServer().getPluginManager(),this);
        // 3. 注册插件命令
        registerCommands();
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
    }
    /**
     * 获取插件单例实例
     * @return WorldBaeter插件实例
     */
    public static WorldBaeter getInstance() {
        return plugin;
    }
}
