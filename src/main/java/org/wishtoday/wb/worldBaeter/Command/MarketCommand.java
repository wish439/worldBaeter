package org.wishtoday.wb.worldBaeter.Command;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;
import org.wishtoday.wb.worldBaeter.GUI.SellItemGUI;
import org.wishtoday.wb.worldBaeter.GUI.TradeGUI;
import org.wishtoday.wb.worldBaeter.Util.ConfigUtils;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

import static org.wishtoday.wb.worldBaeter.GUI.SellItemGUI.*;

public class MarketCommand {
    public static void registerCommand(Commands commands) {
        commands.register(
                Commands.literal("market")
                        .executes(MarketCommand::openGUI)
                        .then(Commands.literal("confirm")
                                .executes(MarketCommand::confirm))
                        .then(Commands.literal("cancel")
                                .then(Commands.literal("trade")
                                        .executes(MarketCommand::cancelTrade))
                                .then(Commands.literal("switchitem")
                                        .executes(MarketCommand::cancelSwitchItem)))
                        .build()
        );
    }

    private static int openGUI(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        new NavGUI().open(player);
        return 1;
    }

    private static int confirm(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        if (!player.getPersistentDataContainer().has(TradeGUI.IS_NEED_CONFIRM, PersistentDataType.BOOLEAN)) {
            player.sendMessage("没有待确认的交易");
            return 0;
        }
        if (Boolean.FALSE.equals(player.getPersistentDataContainer().get(TradeGUI.IS_NEED_CONFIRM, PersistentDataType.BOOLEAN))) {
            player.sendMessage("没有待确认的交易");
            return 0;
        }
        TradeGUI tradeGUI = TradeGUI.tradeGUIs.get(player.getUniqueId());
        if (tradeGUI == null) {
            player.sendMessage("没有待确认的交易");
            return 0;
        }
        //effect start
        player.sendMessage("成功");
        //effect end
        GuiUtils.removePlayerTag(player,TradeGUI.IS_NEED_CONFIRM);
        tradeGUI.open(player);
        TradeGUI.tradeGUIs.remove(player.getUniqueId());
        return 1;
    }
    private static int cancelTrade(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        if (!player.getPersistentDataContainer().has(TradeGUI.IS_NEED_CONFIRM, PersistentDataType.BOOLEAN)) {
            player.sendMessage("没有可取消的交易");
            return 0;
        }
        if (Boolean.FALSE.equals(player.getPersistentDataContainer().get(TradeGUI.IS_NEED_CONFIRM, PersistentDataType.BOOLEAN))) {
            player.sendMessage("没有可取消的交易");
            return 0;
        }
        TradeGUI tradeGUI = TradeGUI.tradeGUIs.get(player.getUniqueId());
        if (tradeGUI == null) {
            player.sendMessage("没有可取消的交易");
            return 0;
        }
        player.sendMessage("已取消交易");
        GuiUtils.removePlayerTag(player,TradeGUI.IS_NEED_CONFIRM);
        tradeGUI.open(player);
        TradeGUI.tradeGUIs.remove(player.getUniqueId());
        return 1;
    }
    private static int cancelSwitchItem(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        if (!player.getPersistentDataContainer().has(IS_CLICKED, PersistentDataType.BOOLEAN)) {
            player.sendMessage("没有可取消的选择");
            return 0;
        }
        SellItemGUI gui = GUI_MAP.get(player.getUniqueId());
        Integer i = player.getPersistentDataContainer().get(PLAYER_CLICK_SLOT, PersistentDataType.INTEGER);
        if (i == null) return 0;
        player.sendMessage("已取消选择");
        GuiUtils.removePlayerTag(player, PLAYER_CLICK_SLOT);
        GuiUtils.removePlayerTag(player, IS_CLICKED);
        GUI_MAP.remove(player.getUniqueId());
        gui.open(player);
        return 1;
    }
}
