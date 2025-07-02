package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.wishtoday.wb.worldBaeter.Config.Config;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.util.*;

public class SellItemGUI extends BaseGUI {
    private static final Material CHOOSENEEDITEM = Material.YELLOW_STAINED_GLASS_PANE;
    @NotNull
    public static final NamespacedKey PLAYER_CLICK_SLOT = Objects.requireNonNull(NamespacedKey.fromString("player_click_slot", WorldBaeter.getInstance()));
    @NotNull
    public static final NamespacedKey IS_CLICKED = Objects.requireNonNull(NamespacedKey.fromString("is_clicked", WorldBaeter.getInstance()));
    @NotNull
    public static final NamespacedKey PLAYER_SELL_COUNT = Objects.requireNonNull(NamespacedKey.fromString("player_sell_count", WorldBaeter.getInstance()));
    public static final Component GUI_NAME = Component.text("Sell Item", NamedTextColor.GOLD);
    public static final Map<UUID, SellItemGUI> GUI_MAP = new HashMap<>();
    private static final int[] needItemSlots = {
            5, 6, 7, 8,
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35,
            41, 42, 43, 44
    };
    private static final int[] itemSlots = {
            0, 1, 2, 3,
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30,
            36, 37, 38, 39
    };

    public SellItemGUI() {
        super(GUI_NAME, GuiUtils.BIGCHESTSIZE);
    }

    @NotNull
    private MarketItemData parseFromSlot(Player player) {
        List<ItemStack> items = ItemUtil.getItems(inventory, itemSlots);
        List<ItemStack> needItemSlot = ItemUtil.getItems(inventory, needItemSlots);
        needItemSlot = needItemSlot.stream().filter(itemStack -> ItemUtil.hasUUIDFromItem(itemStack) && !(itemStack.getType() == CHOOSENEEDITEM)).toList();
        return new MarketItemData(items, needItemSlot, player);
    }

    private void confirm(Player player) {
        if (getSellCount(player) >= Config.max_sell_count) {
            player.closeInventory(InventoryCloseEvent.Reason.PLAYER);
            player.sendMessage("您的售卖次数已达上限");
            return;
        }
        MarketItemData marketItemData = parseFromSlot(player);
        MarketGUI instance = MarketGUI.getInstance();
        instance.addItemToGUI(Objects.requireNonNullElse(marketItemData.getItem().getFirst(),new ItemStack(Material.BARRIER)), marketItemData);
        new NavGUI().open(player);
    }
    private void clearItems() {
        ItemUtil.setItems(inventory,new ItemStack(Material.AIR),itemSlots);
    }
    private int getSellCount(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(PLAYER_SELL_COUNT, PersistentDataType.INTEGER)) return 0;
        Integer i = container.get(PLAYER_SELL_COUNT, PersistentDataType.INTEGER);
        if (i == null) return 0;
        return i;
    }
    private void addOrCreateSellCount(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        Integer i = 1;
        if (container.has(PLAYER_SELL_COUNT, PersistentDataType.INTEGER)) {
            i = container.get(PLAYER_SELL_COUNT, PersistentDataType.INTEGER);
            if (i == null) return;
        } else {
            container.set(PLAYER_SELL_COUNT, PersistentDataType.INTEGER,1);
        }
        container.set(PLAYER_SELL_COUNT, PersistentDataType.INTEGER, i + 1);
    }
    private void removeSellCount(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();
        if (!container.has(PLAYER_SELL_COUNT, PersistentDataType.INTEGER)) return;
        Integer i = container.get(PLAYER_SELL_COUNT, PersistentDataType.INTEGER);
        if (i == null) return;
        container.set(PLAYER_SELL_COUNT, PersistentDataType.INTEGER, i - 1);
    }

    @Override
    public void open(Player player) {
        Bukkit.getServer().getScheduler().runTask(
                WorldBaeter.getInstance(), () -> {
                    player.closeInventory();
                    if (GUI_MAP.containsKey(player.getUniqueId())) {
                        player.openInventory(GUI_MAP.get(player.getUniqueId()).inventory);
                        return;
                    }
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
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage(Component.text("你点击了\"确认交易\""));
                        confirm(player);
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
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage(Component.text("你点击了\"回退界面\""));
                        new NavGUI().open(player);
                    }
            );
        }
        for (int addSize : needItemSlots) {
            addItemNameAndAction(
                    addSize,
                    "选择需要的物品",
                    CHOOSENEEDITEM,
                    (player
                            , item
                            , clickType
                            , action
                            , slot, event) -> {
                        switchItemEffect(player, slot);
                    }
            );
        }
    }

    public void switchItemEffect(
            Player player
            , int slot) {
        player.getPersistentDataContainer().set(PLAYER_CLICK_SLOT, PersistentDataType.INTEGER, slot);
        player.getPersistentDataContainer().set(IS_CLICKED, PersistentDataType.BOOLEAN, true);
        GUI_MAP.put(player.getUniqueId(), this);
        player.closeInventory();
        player.sendMessage(Component.text("请在输入框输入你想要的物品名称(中英文皆可)"));
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);
    }
}
