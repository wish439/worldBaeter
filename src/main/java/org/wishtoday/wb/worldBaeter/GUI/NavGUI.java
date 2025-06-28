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
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;

import java.util.HashMap;
import java.util.Map;

public class NavGUI {
    private static Map<Component, ClickAction> listeners = new HashMap<>();
    private static Map<Integer, ItemStack> addItems = new HashMap<>();
    private static final Component GUI_TITLE = Component.text("World Baeter");
    private static Inventory inventory = GuiUtils.createInventoryWithGlass(GuiUtils.SMALLCHESTSIZE, GUI_TITLE);

    public static int getInventorySize() {
        return inventory.getSize();
    }

    public static void open(Player player) {
        player.closeInventory();
        setInventoryItemFromMap();
        player.openInventory(inventory);
    }

    public static Component getTitle() {
        return GUI_TITLE;
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static Map<Integer, ItemStack> getAddItems() {
        return addItems;
    }

    public static void addItemNameAndAction(
            int slot
            , String name
            , Material material
            , ClickAction action) {
        TextComponent text = Component.text(name);
        addItems.put(slot, ItemUtil.setName(text, material));
        listeners.put(text, action);
    }

    public static Map<Component, ClickAction> getListeners() {
        return listeners;
    }

    public static ClickAction getAction(Component component) {
        return listeners.get(component);
    }

    public static boolean isGUIItem(Component component) {
        return listeners.containsKey(component);
    }

    public static void runAction(Component component
            , Player player
            , ItemStack item
            , ClickType type
            , InventoryAction inventoryAction) {
        getAction(component).click(player,item,type,inventoryAction);
    }

    //可能需要在服务器开启时调用
    public static void initializeInventoryItem() {
        addItemNameAndAction(11
                , "出售"
                , Material.DIAMOND
                ,(player
                        , item
                        , clickType
                        , action) -> {
            player.sendMessage(Component.text("你点击了\"出售\""));
                });
        addItemNameAndAction(
                12,
                "浏览商品"
                , Material.IRON_INGOT,
                (player
                        , item
                        , clickType
                        , action) -> {
                    player.sendMessage(Component.text("你点击了\"浏览商品\""));
                }
        );
    }

    public static void setInventoryItemFromMap() {
        for (Map.Entry<Integer, ItemStack> entry : addItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }
}
