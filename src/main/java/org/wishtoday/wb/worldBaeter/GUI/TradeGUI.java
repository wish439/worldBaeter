package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;

import java.util.List;

import static org.wishtoday.wb.worldBaeter.GUI.SellItemGUI.needItemSlots;
import static org.wishtoday.wb.worldBaeter.GUI.SellItemGUI.itemSlots;

public class TradeGUI extends BaseGUI {
    public static final Component TRADE_TITLE = Component.text("Trade", NamedTextColor.GOLD);

    private TradeGUI(MarketItemData market) {
        super(TRADE_TITLE, GuiUtils.BIGCHESTSIZE);
        parseMarketItemData(market);
    }

    private void parseMarketItemData(MarketItemData market) {
        List<ItemStack> item = market.getItem();
        List<ItemStack> needItem = market.getNeedItem();

        for (int i = 0; i < item.size(); i++) {
            ItemStack stack = item.get(i);
            inventory.setItem(itemSlots[i], stack);
        }
        for (int i = 0; i < needItem.size(); i++) {
            ItemStack stack = needItem.get(i);
            inventory.setItem(needItemSlots[i], stack);
        }
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
                    (player, item1, clickType, action, slot, event) -> {
                        player.sendMessage(Component.text("你点击了\"回退界面\""));
                        MarketGUI.getInstance().open(player);
                    }
            );
        }
        return inventory;
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
