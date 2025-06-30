package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.Impls.ClickAction;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;
import org.wishtoday.wb.worldBaeter.Util.MarketItemData;
import org.wishtoday.wb.worldBaeter.Util.PlayerData;

import java.util.*;

public class MarketGUI extends BaseGUI {
    public static Component title = Component.text("Market GUI", NamedTextColor.GREEN);
    private static MarketGUI instance;
    private List<Inventory> invs;
    private final Map<ItemStack, MarketItemData> items;


    private MarketGUI() {
        super(title, GuiUtils.BIGCHESTSIZE);
        invs = new ArrayList<>();
        invs.add(inventory);
        items = new HashMap<>();
        /*ArrayList<ItemStack> list1 = new ArrayList<>();
        list1.add(new ItemStack(Material.DIRT));
        ArrayList<ItemStack> list2 = new ArrayList<>();
        list2.add(new ItemStack(Material.DIAMOND));
        MarketItemData marketItemData = new MarketItemData(list1, list2, new PlayerData(UUID.randomUUID(), "Test"));
        for (int i = 0; i < 44; i++) {
            addItemToGUI(new ItemStack(Material.BARRIER),marketItemData);
        }*/
    }

    @Override
    public void open(Player player, int index) {
        player.closeInventory();
        Inventory first = invs.get(index);
        player.openInventory(first);
    }

    public void addItemAndAction(
            ItemStack item
            , MarketItemData itemData
            , ClickAction action) {
        UUID uuid = ItemUtil.addUUIDToItem(item);
        listeners.put(uuid, action);
        items.put(item, itemData);
    }

    public void addItemToGUI(
            ItemStack item
            , MarketItemData itemData) {
        addItemAndAction(item, itemData,
                (player
                        , item1
                        , clickType
                        , action
                        , slot
                        , event) -> {

                });
        reloadInventory();
    }

    public void addItemAndAuto() {
        Inventory last = invs.getLast();
        for (Map.Entry<ItemStack, MarketItemData> entry : items.entrySet()) {
            if (GuiUtils.isFull(last, last.getSize())) {
                invs.add(GuiUtils.cloneInventory(inventory));
                initializeItems();
            }
            last.addItem(entry.getKey());
        }
        System.out.println("addItemAndAuto" + invs.size());
    }

    public void reloadInventory() {
        invs.clear();
        initializeItems();
        invs.add(inventory);
        Inventory last = invs.getLast();
        last.clear();
        initializeItems();
        addItemAndAuto();
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return Bukkit.createInventory(null, size, title);
    }

    @Override
    protected void initializeItems() {
        inventory.clear();
        for (int i = 45; i < 48; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "上一页",
                    Material.DIAMOND,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了上一页");
                        open(player,0);
                    }
            );
        }
        for (int i = 48; i < 51; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "回退",
                    Material.IRON_INGOT,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了回退");
                        new NavGUI().open(player);
                    }
            );
        }
        for (int i = 51; i < 54; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "下一页",
                    Material.RED_STAINED_GLASS,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了下一页");
                        open(player,1);
                    }
            );
        }
    }

    public static MarketGUI getInstance() {
        if (instance == null) {
            instance = new MarketGUI();
        }
        return instance;
    }
}
