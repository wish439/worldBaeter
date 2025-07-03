package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.util.*;

import static org.wishtoday.wb.worldBaeter.GUI.SellItemGUI.needItemSlots;
import static org.wishtoday.wb.worldBaeter.GUI.SellItemGUI.itemSlots;

public class TradeGUI extends BaseGUI {
    public static final Component TRADE_TITLE = Component.text("Trade", NamedTextColor.GOLD);
    @NotNull
    public static final NamespacedKey IS_NEED_CONFIRM = Objects.requireNonNull(NamespacedKey.fromString("is_need_confirm", WorldBaeter.getInstance()));
    public static final Map<UUID, TradeGUI> tradeGUIs = new HashMap<>();

    private TradeGUI(MarketItemData market) {
        super(TRADE_TITLE, GuiUtils.BIGCHESTSIZE);
        parseMarketItemData(market);
    }

    private void parseMarketItemData(MarketItemData market) {
        List<ItemStack> item = market.getItem();
        List<ItemStack> needItem = market.getNeedItem();
        if (needItem.isEmpty()) System.out.println("Empty needItem");

        for (int i = 0; i < item.size(); i++) {
            ItemStack stack = item.get(i);
            ItemUtil.setChineseItemFromEnglish(stack);
            inventory.setItem(itemSlots[i], stack);
        }
        for (int i = 0; i < needItem.size(); i++) {
            ItemStack stack = needItem.get(i);
            ItemUtil.setChineseItemFromEnglish(stack);
            inventory.setItem(needItemSlots[i], stack);
        }
    }

    @Override
    public void open(Player player) {
        Bukkit.getServer().getScheduler().runTask(
                WorldBaeter.getInstance(), () -> {
                    player.closeInventory();
                    if (tradeGUIs.containsKey(player.getUniqueId())) {
                        player.openInventory(tradeGUIs.get(player.getUniqueId()).inventory);
                        return;
                    }
                    super.open(player);
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
        inventory = GuiUtils.createInventoryWithGlass(title, size, addSizes);
        int startIndex = GuiUtils.BIGCHESTSIZE - 9;
        int endIndex = GuiUtils.BIGCHESTSIZE - 5;
        for (int i = startIndex; i < endIndex; i++) {
            addItemNameAndAction(
                    i,
                    "确认交易",
                    Material.GREEN_STAINED_GLASS_PANE,
                    (player, item1, clickType, action, slot, event) -> {
                        player.sendMessage(
                                Component.text("✅ 你点击了", NamedTextColor.GREEN)
                                        .append(Component.text("【确认交易】", NamedTextColor.YELLOW))
                                        .append(Component.text("按钮！", NamedTextColor.GREEN))
                                        .decoration(TextDecoration.ITALIC, false)
                        );
                        confirmBuy(player);
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
                    (player, item1, clickType, action, slot, event) -> {
                        player.sendMessage(
                                Component.text("🔙 你点击了", NamedTextColor.GREEN)
                                        .append(Component.text("【回退界面】", NamedTextColor.YELLOW))
                                        .append(Component.text("按钮！", NamedTextColor.GREEN))
                                        .decoration(TextDecoration.ITALIC, false)
                        );
                        MarketGUI.getInstance().open(player);
                    }
            );

        }
        return inventory;
    }

    private void confirmBuy(Player player) {
        player.getPersistentDataContainer().set(IS_NEED_CONFIRM, PersistentDataType.BOOLEAN, true);
        tradeGUIs.put(player.getUniqueId(), this);
        player.closeInventory();

        // 优化后的分隔提示
        player.sendMessage(
                Component.text("======= 交易未完成 =======", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        );
        player.sendMessage(
                Component.text("请输入 /market confirm 以确认交易。", NamedTextColor.GREEN)
                        .decoration(TextDecoration.ITALIC, false)
        );
    }


    @Override
    protected void initializeItems() {

    }

    public static TradeGUI getGUI(MarketItemData marketItemData) {
        return new TradeGUI(marketItemData);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
