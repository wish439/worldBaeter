package org.wishtoday.wb.worldBaeter.Command;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;
import org.wishtoday.wb.worldBaeter.GUI.TradeGUI;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class MarketCommand {
    public static void registerCommand(Commands commands) {
        commands.register(
                Commands.literal("market")
                        .executes(MarketCommand::openGUI)
                        .then(Commands.literal("confirm")
                                .executes(MarketCommand::confirm))
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
}
