package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class MarketGUI extends BaseGUI {
    public static Component title = Component.text("Market GUI", NamedTextColor.GREEN);
    private static MarketGUI instance;
    private List<Inventory> invs;


    private MarketGUI() {
        super(title, GuiUtils.BIGCHESTSIZE);
        invs = new ArrayList<>();
        invs.add(inventory);
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return Bukkit.createInventory(null, size, title);
    }

    @Override
    protected void initializeItems() {
        for (int i = 45; i < 48; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "上一页",
                    Material.DIAMOND,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了上一页");
                    }
            );
        }
        for (int i = 48; i < 51; i++) {
            addItemNameAndActionAutoRefresh(
                    i,
                    "上一页",
                    Material.DIAMOND,
                    (player, item, clickType, action, slot, event) -> {
                        player.sendMessage("你点击了回退");
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
