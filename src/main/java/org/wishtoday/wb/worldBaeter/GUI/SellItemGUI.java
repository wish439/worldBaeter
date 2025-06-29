package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SellItemGUI extends BaseGUI {
    @NotNull
    public static final NamespacedKey PLAYER_CLICK_SLOT = NamespacedKey.fromString("player_click_slot", WorldBaeter.getInstance());
    @NotNull
    public static final NamespacedKey IS_CLICKED = NamespacedKey.fromString("is_clicked", WorldBaeter.getInstance());
    public static final Component GUI_NAME = Component.text("Sell Item", NamedTextColor.GOLD);
    public static final Map<UUID, SellItemGUI> GUI_MAP = new HashMap<>();

    public SellItemGUI() {
        super(GUI_NAME, GuiUtils.BIGCHESTSIZE);
    }

    @Override
    public void open(Player player) {
        Bukkit.getServer().getScheduler().runTask(
                WorldBaeter.getInstance(), () -> {
                    player.closeInventory();
                    player.openInventory(inventory);
                }
        );
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        int[] addSizes = {
                4,
                4 + 9,
                4 + 9 + 9,
                4 + 9 + 9 + 9,
                4 + 9 + 9 + 9 + 9,
                4 + 9 + 9 + 9 + 9 + 9,
        };
        return GuiUtils.createInventoryWithGlass(title, size, addSizes);
    }

    @Override
    public void initializeItems() {
        int startIndex = GuiUtils.BIGCHESTSIZE - 9;
        int endIndex = GuiUtils.BIGCHESTSIZE - 5;
        for (int i = startIndex; i < endIndex; i++) {
            addItemNameAndAction(
                    i,
                    "确认交易",
                    Material.GREEN_STAINED_GLASS_PANE,
                    (player, item, clickType, action, slot) -> {
                        player.sendMessage(Component.text("你点击了\"确认交易\""));
                    }
            );
        }
        int startIndex1 = GuiUtils.BIGCHESTSIZE - 4;
        int endIndex1 = GuiUtils.BIGCHESTSIZE;
        for (int i = startIndex1; i < endIndex1; i++) {
            addItemNameAndAction(
                    i,
                    "回退界面",
                    Material.RED_STAINED_GLASS_PANE,
                    (player, item, clickType, action, slot) -> {
                        player.sendMessage(Component.text("你点击了\"回退界面\""));
                        new NavGUI().open(player);
                    }
            );
        }
        int[] slots = {
                5, 6, 7, 8,
                14, 15, 16, 17,
                23, 24, 25, 26,
                32, 33, 34, 35,
                41, 42, 43, 44
        };
        for (int addSize : slots) {
            addItemNameAndAction(
                    addSize,
                    "选择需要的物品",
                    Material.YELLOW_STAINED_GLASS_PANE,
                    (player
                            , item
                            , clickType
                            , action
                            , slot) -> {
                        switchItemEffect(player, slot, item, clickType, action);
                    }
            );
        }
    }

    private void switchItemEffect(
            Player player
            , int slot
            , ItemStack stack
            , ClickType clickType
            , InventoryAction action) {
        player.getPersistentDataContainer().set(PLAYER_CLICK_SLOT, PersistentDataType.INTEGER, slot);
        player.getPersistentDataContainer().set(IS_CLICKED, PersistentDataType.BOOLEAN, true);
        GUI_MAP.put(player.getUniqueId(), this);
        player.closeInventory();
        player.sendMessage(Component.text("请在输入框输入你想要的物品名称(中英文皆可)"));
    }
}
