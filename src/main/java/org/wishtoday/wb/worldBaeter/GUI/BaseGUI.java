package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.Impls.ClickAction;
import org.wishtoday.wb.Impls.GUIInterface;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseGUI implements GUIInterface {
    protected final Map<UUID, ClickAction> listeners = new HashMap<>();
    protected final Map<Integer, ItemStack> addItems = new HashMap<>();
    protected Inventory inventory;
    protected final Component title;
    protected final int size;

    public BaseGUI(Component title, int size) {
        this.title = title;
        this.size = size;
        this.inventory = createInitialInventory(size, title);
        initializeItems();
        populateItems();
        GUIManager.addGUI(this);
    }

    @Override
    public void open(Player player, int index) {
        open(player);
    }

    public Map<UUID, ClickAction> getListeners() {
        return listeners;
    }

    public Map<Integer, ItemStack> getAddItems() {
        return addItems;
    }

    public abstract Inventory createInitialInventory(int size, Component title);

    @Override
    public void open(Player player) {
        player.closeInventory();
        initializeItems();
        GUIManager.addToPlayerGUIMap(player, this);
        player.openInventory(inventory);
    }

    @Override
    public boolean isGUIItem(UUID uuid) {
        return listeners.containsKey(uuid);
    }

    protected abstract void initializeItems();

    public void addItem(int slot, String name, Material material, ClickAction action) {
        LegacyComponentSerializer legacy = LegacyComponentSerializer.builder()
                .hexColors()
                .character('§')
                .build();
        TextComponent text;
        if(name.indexOf('§') >= 0){
            text = (TextComponent) legacy.deserialize(name);
        }else{
            text = Component.text(name);
        }
        UUID uuid = UUID.randomUUID();
        addItems.put(slot, ItemUtil.setName(text, material, uuid));
        listeners.put(uuid, action);
    }

    public void addItemNameAndAction(int slot, ItemStack item, ClickAction action) {
        UUID uuid = ItemUtil.addUUIDToItem(item);
        addItems.put(slot, item);
        listeners.put(uuid, action);
    }
    public void addItemNameAndAction(ItemStack item, ClickAction action) {
        UUID uuid = ItemUtil.addUUIDToItem(item);
        inventory.addItem(item);
        listeners.put(uuid, action);
    }


    public void addItemNameAndAction(int slot
            , String name
            , Material material
            , ClickAction action) {
        LegacyComponentSerializer legacy = LegacyComponentSerializer.builder()
                .hexColors()
                .character('§')
                .build();
        TextComponent text;
        if(name.indexOf('§') >= 0){
            text = (TextComponent) legacy.deserialize(name);
        }else{
            text = Component.text(name);
        }// 创建文本组件
        UUID uuid = UUID.randomUUID();
        // 通过工具类创建带名称的物品，并存入映射
        addItems.put(slot, ItemUtil.setName(text, material,uuid));
        // 关联文本组件和点击动作（用于后续事件触发）
        listeners.put(uuid, action);
    }

    public void addItemNameAndActionAutoRefresh(
            int slot
            , String name
            , Material material
            , ClickAction action) {
        LegacyComponentSerializer legacy = LegacyComponentSerializer.builder()
                .hexColors()
                .character('§')
                .build();
        TextComponent text;
        if(name.indexOf('§') >= 0){
            text = (TextComponent) legacy.deserialize(name);
        }else{
            text = Component.text(name);
        }// 创建文本组件
        UUID uuid = UUID.randomUUID();
        addItems.put(slot, ItemUtil.setName(text, material,uuid));
        // 关联文本组件和点击动作（用于后续事件触发）
        listeners.put(uuid, action);
        populateItems();
    }

    public void addItemNameAndActionAutoRefresh(
            String name
            , Material material
            , ClickAction action) {
        LegacyComponentSerializer legacy = LegacyComponentSerializer.builder()
                .hexColors()
                .character('§')
                .build();
        TextComponent text;
        if(name.indexOf('§') >= 0){
            text = (TextComponent) legacy.deserialize(name);
        }else{
            text = Component.text(name);
        }// 创建文本组件
        UUID uuid = UUID.randomUUID();
        inventory.addItem(ItemUtil.setName(text, material,uuid));
        // 关联文本组件和点击动作（用于后续事件触发）
        listeners.put(uuid, action);
    }


    // 填充物品到库存
    private void populateItems() {
        for (Map.Entry<Integer, ItemStack> entry : addItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void runAction(UUID uuid
            , Player player
            , ItemStack item
            , ClickType clickType
            , InventoryAction inventoryAction
            , int slot
            , InventoryClickEvent event) {
        if (listeners.get(uuid) == null) return;
        listeners.get(uuid).click(player, item, clickType, inventoryAction, slot, event);
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        //如果界面需要在关闭时执行逻辑,请写在此处
        GUIManager.removeGUI(event.getView().title());
        GUIManager.removeFromPlayerGUIMap((Player) event.getPlayer());
    }
}
