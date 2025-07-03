package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.Impls.GUIInterface;
import org.wishtoday.wb.worldBaeter.Util.ItemUtil;

import java.util.*;

public class GUIManager {
    private static final Map<Component, GUIInterface> guiMap = new HashMap<>();
    private static final Map<UUID, GUIInterface> playerGUIMap = new HashMap<>();
    private static final Set<Component> GuiName = new HashSet<>();

    public static void addToPlayerGUIMap(Player player, GUIInterface gui) {
        playerGUIMap.put(player.getUniqueId(), gui);
    }

    public static void removeFromPlayerGUIMap(Player player) {
        playerGUIMap.remove(player.getUniqueId());
    }

    public static GUIInterface getGUI(Player player) {
        return playerGUIMap.get(player.getUniqueId());
    }

    public static boolean hasGUI(Player player) {
        return playerGUIMap.containsKey(player.getUniqueId());
    }

    public static void addGUI(GUIInterface gui) {
        guiMap.put(gui.getTitle(), gui);
        GuiName.add(gui.getTitle());
    }

    public static GUIInterface getGUI(Component component) {
        return guiMap.get(component);
    }

    public static boolean hasGUI(Component component) {
        return guiMap.containsKey(component);
    }

    public static void removeGUI(Component component) {
        guiMap.remove(component);
    }

    public static void handlerActions(InventoryClickEvent event) {
        Component title = event.getView().title();
        HumanEntity humanEntity = event.getWhoClicked();
        if (!(humanEntity instanceof Player player)) return;
        if (!GuiName.contains(title)) return;
        GUIInterface gui = getGUI(player);
        if (gui == null) return;
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (!gui.isGUIItem(ItemUtil.getUUIDFromItem(item))) return;
        event.setCancelled(true);
        Component component = item.getItemMeta().displayName();
        UUID uuid = ItemUtil.getUUIDFromItem(item);
        gui.runAction(uuid
                , player
                , item
                , event.getClick()
                , event.getAction()
                , event.getSlot()
                , event);
    }

    public static void handlerClose(InventoryCloseEvent event) {
        Component title = event.getView().title();
        HumanEntity humanEntity = event.getPlayer();
        GUIInterface gui = getGUI(title);
        if (gui == null) return;
        if (!(humanEntity instanceof Player)) return;
        if (!event.getView().title().contains(gui.getTitle(),Component.EQUALS)) return;
        gui.onClose(event);
    }
    public static void handlerOtherClicks(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getWhoClicked();
        if (!(humanEntity instanceof Player player)) return;
        GUIInterface gui = getGUI(player);
        if (gui == null) return;
        if (!event.getView().title().contains(gui.getTitle(),Component.EQUALS)) return;
        gui.onClick(event);
    }
}
