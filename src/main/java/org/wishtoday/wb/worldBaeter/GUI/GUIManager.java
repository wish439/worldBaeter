package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.Impls.GUIInterface;

import java.util.HashMap;
import java.util.Map;

public class GUIManager {
    private static final Map<Component, GUIInterface> guiMap = new HashMap<>();

    public static void addGUI(GUIInterface gui) {
        guiMap.put(gui.getTitle(), gui);
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
        GUIInterface gui = getGUI(title);
        if (gui == null) return;
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (!gui.isGUIItem(item.getItemMeta().displayName())) return;
        event.setCancelled(true);
        Component component = item.getItemMeta().displayName();
        HumanEntity humanEntity = event.getWhoClicked();
        if (!(humanEntity instanceof Player player)) return;
        gui.runAction(component
                , player
                , item
                , event.getClick()
                , event.getAction()
                , event.getSlot()
                , event);
    }
}
