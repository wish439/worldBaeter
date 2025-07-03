package org.wishtoday.wb.worldBaeter.Events.impl;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.wishtoday.wb.worldBaeter.GUI.SellItemGUI;
import org.wishtoday.wb.worldBaeter.Util.ConfigUtils;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;

import static org.wishtoday.wb.worldBaeter.GUI.SellItemGUI.*;

public class MessageEvents implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!player.getPersistentDataContainer().has(IS_CLICKED, PersistentDataType.BOOLEAN))
            return;
        event.setCancelled(true);
        Component message = event.message();
        Material itemType = ConfigUtils.searchMap(message);
        SellItemGUI gui = GUI_MAP.get(player.getUniqueId());
        Integer i = player.getPersistentDataContainer().get(PLAYER_CLICK_SLOT, PersistentDataType.INTEGER);
        if (i == null) return;
        if (itemType != null) {
            ItemStack stack = new ItemStack(itemType);
            ItemUtil.setChineseItemFromEnglish(stack);
            gui.addItemNameAndActionAutoRefresh(
                    i
                    , stack
                    , (player1, item, clickType, action, slot, clickEvent) -> {
                        Inventory inventory = clickEvent.getClickedInventory();
                        if (inventory == null) return;
                        if (clickType.equals(ClickType.LEFT)) inventory.setItem(slot, item.add(1));
                        if (clickType.equals(ClickType.RIGHT)) {
                            if (item.getAmount() - 1 == 0) {
                                gui.addItemNameAndActionAutoRefresh(
                                        slot,
                                        "选择需要的物品",
                                        Material.YELLOW_STAINED_GLASS_PANE,
                                        (player2
                                                , item2
                                                , clickType2
                                                , action2
                                                , slot2, event2) -> {
                                            gui.switchItemEffect(player2, slot2);
                                        }
                                );
                            } else {
                                inventory.setItem(slot, item.add(-1));
                            }
                        }
                    }
            );
        } else {
            player.sendMessage("未查询到对应物品,请核实后重新尝试");
        }
        GuiUtils.removePlayerTag(player, PLAYER_CLICK_SLOT);
        GuiUtils.removePlayerTag(player, IS_CLICKED);
        GUI_MAP.remove(player.getUniqueId());
        gui.open(player);
    }
}
