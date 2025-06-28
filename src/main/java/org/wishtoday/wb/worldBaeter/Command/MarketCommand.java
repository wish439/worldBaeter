package org.wishtoday.wb.worldBaeter.Command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.GUI.MarketGUI;

public class MarketCommand {
    public static void registerCommand(Commands commands) {
        commands.register(
                Commands.literal("market")
                        .executes(MarketCommand::openGUI)
                        .then(Commands.literal("add")
                                .then(Commands.argument("item_name", StringArgumentType.string())
                                .executes(MarketCommand::addToPlayerMarkets)
                                .then(Commands.argument("item_count", IntegerArgumentType.integer())
                                        .executes(MarketCommand::addToPlayerMarkets))
                        ))
                        .build()
        );
    }

    private static int addToPlayerMarkets(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        Integer count = context.getArgument("item_count", Integer.class);
        String name = context.getArgument("item_name", String.class);
        Material material = findMaterial(name);
        if (material == null) return 0;
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (mainHand.getType() == Material.AIR) {
            player.sendMessage("你的手中没有物品");
            return 0;
        }
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        ItemStack item = new ItemStack(material, count);
        MarketGUI.addToItems(player, mainHand, item);
        return 1;
    }
    private static Material findMaterial(String s) {
        for (Material value : Material.values()) {
            if (value.name().equalsIgnoreCase(s)) return value;
        }
        return null;
    }

    private static int openGUI(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (!(sender instanceof Player player)) return 0;
        MarketGUI.openInventory(player);
        return 1;
    }
}
