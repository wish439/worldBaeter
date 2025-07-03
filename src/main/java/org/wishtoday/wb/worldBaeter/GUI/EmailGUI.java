package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

public class EmailGUI extends BaseGUI {
    public static final Component EMAIL_TITLE = Component.text("email", NamedTextColor.AQUA);

    public EmailGUI() {
        super(EMAIL_TITLE, GuiUtils.BIGCHESTSIZE);
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return GuiUtils.createInventoryWithGlass(size, title);
    }

    @Override
    protected void initializeItems() {

    }
}
