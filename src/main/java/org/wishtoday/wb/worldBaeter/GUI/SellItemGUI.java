package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class SellItemGUI extends BaseGUI{
    public static final Component GUI_NAME = Component.text("Sell Item", NamedTextColor.GOLD);

    public SellItemGUI() {
        super(GUI_NAME, GuiUtils.BIGCHESTSIZE);
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return GuiUtils.createInventoryWithGlass(title,size);
    }

    @Override
    public void initializeItems() {
        int startIndex = GuiUtils.BIGCHESTSIZE - 9;
        int endIndex = GuiUtils.BIGCHESTSIZE - 5;
        for (int i = startIndex; i < endIndex; i++) {
            addItemNameAndAction(
                    i,
                    "确认交易",
                    Material.GREEN_STAINED_GLASS_PANE,
                    (player, item, clickType, action) -> {
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
                    (player, item, clickType, action) -> {
                        player.sendMessage(Component.text("你点击了\"回退界面\""));
                        new NavGUI().open(player);
                    }
            );
        }
    }
}
