package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.Impls.ClickAction;
import org.wishtoday.wb.Impls.GUIInterface;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseGUI implements GUIInterface {
    protected final Map<Component, ClickAction> listeners = new HashMap<>();
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
    public abstract Inventory createInitialInventory(int size, Component title);
    @Override
    public void open(Player player) {
        player.closeInventory();
        initializeItems();
        player.openInventory(inventory);
    }

    @Override
    public boolean isGUIItem(Component component) {
        return listeners.containsKey(component);
    }

    protected abstract void initializeItems();
    protected void addItem(int slot, String name, Material material, ClickAction action) {
        TextComponent text = Component.text(name);
        addItems.put(slot, ItemUtil.setName(text, material));
        listeners.put(text, action);
    }

    protected void addItemNameAndAction(int slot, ItemStack item, ClickAction action) {
        Component displayName = item.getItemMeta().displayName();
        if (displayName != null) {
            addItems.put(slot, item);
            listeners.put(displayName, action);
        }
    }
    protected void addItemNameAndAction(int slot
            , String name
            , Material material
            , ClickAction action) {
        TextComponent text = Component.text(name);// 创建文本组件
        // 通过工具类创建带名称的物品，并存入映射
        addItems.put(slot, ItemUtil.setName(text, material));
        // 关联文本组件和点击动作（用于后续事件触发）
        listeners.put(text, action);
    }

    // 填充物品到库存
    private void populateItems() {
        for (Map.Entry<Integer, ItemStack> entry : addItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void runAction(Component name
            , Player player
            , ItemStack item
            , ClickType clickType
            , InventoryAction inventoryAction
            , int slot) {
        if (listeners.get(name) == null) return;
        listeners.get(name).click(player, item, clickType, inventoryAction,slot);
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
