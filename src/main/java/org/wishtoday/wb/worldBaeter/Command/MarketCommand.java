package org.wishtoday.wb.worldBaeter.Command;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.wishtoday.wb.worldBaeter.GUI.NavGUI;

public class MarketCommand {
    public static void registerCommand(Commands commands) {
        commands.register(
                Commands.literal("market")
                        .executes(MarketCommand::openGUI)
                        .build()
        );
    }

    private static int openGUI(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        new NavGUI().open(player);
        return 1;
    }
}
