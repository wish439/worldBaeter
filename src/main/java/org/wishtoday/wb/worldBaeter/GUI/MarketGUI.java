package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.wishtoday.wb.Impls.ClickAction;
import org.wishtoday.wb.worldBaeter.Config.Config;
import org.wishtoday.wb.worldBaeter.Util.*;

import java.util.Map;
import java.util.List;

import java.util.*;

public class MarketGUI extends BaseGUI {
    // 使用渐变工具类创建标题
    public static Component title = TextGradient.createGradient(
            "交易市场",
            NamedTextColor.WHITE,
            NamedTextColor.DARK_GRAY,
            NamedTextColor.GOLD
    );

    private static MarketGUI instance;
    private List<Inventory> invs;
    private Map<ItemStack, MarketItemData> items;
    /**
     * 商品上架编号计数器
     */
    private int listingIndex;

    private MarketGUI() {
        super(title, GuiUtils.BIGCHESTSIZE);
        invs = new ArrayList<>();
        invs.add(inventory);
        items = new LinkedHashMap<>();
        Map<ItemStack, MarketItemData> map = Config.loadItemStackMap();
        items.putAll(map);
        // 初始化编号计数器（已存在的商品数量）
        listingIndex = items.size();
        sortAndReload();
        //reloadInventory();
        /*ArrayList<ItemStack> list1 = new ArrayList<>();
        list1.add(new ItemStack(Material.DIRT));
        ArrayList<ItemStack> list2 = new ArrayList<>();
        list2.add(new ItemStack(Material.DIAMOND));
        MarketItemData marketItemData = new MarketItemData(list1, list2, new PlayerData(UUID.randomUUID(), "Test"));
        for (int i = 0; i < 44; i++) {
            addItemToGUI(new ItemStack(Material.BARRIER),marketItemData);
        }*/
    }

    private void sortAndReload() {
        ArrayList<Map.Entry<ItemStack, MarketItemData>> list = new ArrayList<>(items.entrySet());
        list.sort(Comparator.comparingInt(o -> o.getValue().getTag()));
        /*list.sort((o1, o2) ->
            o2.getValue().getTag() - o1.getValue().getTag()
        );*/
        for (int i = 0; i < list.size(); i++) {
            Map.Entry<ItemStack, MarketItemData> entry = list.get(i);
            entry.getValue().setTag(i + 1);
        }
        LinkedHashMap<ItemStack, MarketItemData> map = new LinkedHashMap<>();
        for (Map.Entry<ItemStack, MarketItemData> entry : list) {
            ItemStack key = entry.getKey();
            if (key.getItemMeta().hasDisplayName()) {
                ItemMeta meta = key.getItemMeta();
                meta.displayName(Component.text(entry.getValue().getTag()).append(key.getItemMeta().displayName()));
                key.setItemMeta(meta);
            }
            map.put(key, entry.getValue());
        }
        items = map;
        reloadInventory();
    }

    public void setItems(Map<ItemStack, MarketItemData> map) {
        this.items = map;
    }

    @Override
    public void open(Player player, int index) {
        player.closeInventory();
        Inventory first = invs.get(index);
        GUIManager.addToPlayerGUIMap(player, this);
        sortAndReload();
        player.openInventory(first);
    }

    @Override
    public void open(Player player) {
        open(player, 0);
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
// 为商品生成自动编号
        int id = ++listingIndex;
        String idStr = String.format("%02d", id);
// 在物品显示名前加上编号
        if (item != null && item.getItemMeta() != null) {
            org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();
            Component originalName = meta.hasDisplayName() ? meta.displayName() : Component.text(item.getType().translationKey());
            meta.displayName(Component.text(idStr + " ").append(originalName));
            item.setItemMeta(meta);
        }


        addItemAndAction(item, itemData,
                (player
                        , item1
                        , clickType
                        , action
                        , slot
                        , event) -> {

                });
        //reloadInventory();
        sortAndReload();
    }

    public void addItemAndAuto() {
        Inventory last = invs.getLast();
        for (Map.Entry<ItemStack, MarketItemData> entry : items.entrySet()) {
            if (GuiUtils.isFull(last, last.getSize())) {
                invs.addFirst(GuiUtils.cloneInventory(inventory));
                initializeItems();
            }
            UUID uuid = ItemUtil.addUUIDToItem(entry.getKey());
            listeners.put(uuid,
                    (player,
                     item,
                     clickType,
                     action,
                     slot,
                     event) -> {
                        MarketItemData data = items.get(item);
                        TradeGUI.getGUI(data).open(player);
                    });
            last.addItem(entry.getKey());
        }
        Config.save(items);
        //Config.setConfig("market_items", items);
        //System.out.println("addItemAndAuto" + invs.size());
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
        for (int i = 46; i < 47; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "上一页",
                    Material.OAK_BUTTON,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了上一页");
                        open(player, 0);
                    }
            );
        }
        for (int i = 49; i < 50; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "回退",
                    Material.BELL,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了回退");
                        new NavGUI().open(player);
                    }
            );
        }
        for (int i = 52; i < 53; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "下一页",
                    Material.ARROW,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了下一页");
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
    }

    public static MarketGUI getInstance() {
        if (instance == null) {
            instance = new MarketGUI();
        }
        return instance;
    }
}
