package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.Config.Config;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

import java.util.*;

public class EmailGUI extends BaseGUI {
    public static final Component EMAIL_TITLE = Component.text("email", NamedTextColor.AQUA);
    private static final Map<UUID, EmailGUI> emails = new HashMap<>();
    public static Map<UUID, List<ItemStack>> emailItems = new HashMap<>();

    private EmailGUI() {
        super(EMAIL_TITLE, GuiUtils.BIGCHESTSIZE);
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return GuiUtils.createInventoryWithGlass(title, size, GuiUtils.getAllSlot(GuiUtils.BIGCHESTSIZE));
    }

    @Override
    protected void initializeItems() {
        inventory.clear();
        for (int i = 46; i < 47; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "§b← 上一页",
                    Material.OAK_BUTTON,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("§e正在返回上一页...");
                        open(player, 0);
                    }
            );
        }

        for (int i = 49; i < 50; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "§c返回主菜单",
                    Material.BARRIER,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("§e你已返回到主菜单界面");
                        new NavGUI().open(player);
                    }
            );
        }

        for (int i = 52; i < 53; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "§a下一页 →",
                    Material.ARROW,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("§e正在翻到下一页...");
                        open(player, 1);
                    }
            );
        }

        for (int i = 45; i < 54; i++) {
            if (i == 46 || i == 49 || i == 52) continue;
            addItemNameAndActionAutoRefresh(
                    i,
                    " ",
                    Material.GRAY_STAINED_GLASS_PANE,
                    (player, item, clickType, action, slot, event) -> {
                        event.setCancelled(true); // 阻止任何交互
                    }
            );
        }

        int i = 45;
        while (i < 54) {
            if (i == 46 || i == 49 || i == 52) {
                i++;
                continue;
            }
            addItemNameAndActionAutoRefresh(
                    i,
                    " ",
                    Material.GRAY_STAINED_GLASS_PANE,
                    (player, item, clickType, action, slot, event) -> {
                        event.setCancelled(true); // 阻止任何交互
                    }
            );
            i++;
        }
    }

    @Override
    public void open(Player player) {
        player.closeInventory();
        initializeItems();
        addItemsToEmail(emailItems.get(player.getUniqueId()), player);
        GUIManager.addToPlayerGUIMap(player, this);
        player.openInventory(inventory);
    }

    public static EmailGUI getGUIFromPlayer(Player player) {
        return emails.get(player.getUniqueId());
    }

    public static void createEmailToPlayer(Player player) {
        if (emails.containsKey(player.getUniqueId())) return;
        EmailGUI gui = new EmailGUI();
        emails.put(player.getUniqueId(), gui);
    }

    public void addItemToEmail(ItemStack stack, Player player) {
        int i = GuiUtils.getFilterItemSlotCount(inventory);
        inventory.setItem(i, stack);
        emailItems.get(player.getUniqueId()).add(stack);
        Config.saveEmail();
    }

    public void addItemsToEmail(List<ItemStack> stack, Player player) {
        if (stack == null) return;
        for (ItemStack itemStack : stack) {
            addItemToEmail(itemStack,player);
        }
    }
}