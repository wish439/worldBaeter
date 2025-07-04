package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EmailGUI extends BaseGUI {
    public static final Component EMAIL_TITLE = Component.text("email", NamedTextColor.AQUA);
    private static Map<UUID, EmailGUI> emails = new HashMap<>();

    private EmailGUI() {
        super(EMAIL_TITLE, GuiUtils.BIGCHESTSIZE);
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return GuiUtils.createInventoryWithGlass(title, size, GuiUtils.getAllSlot(GuiUtils.BIGCHESTSIZE));
    }

    @Override
    protected void initializeItems() {
        int bigchestsize = GuiUtils.BIGCHESTSIZE;
        int i = bigchestsize - 9;
        for (; i < bigchestsize; i++) {
            addItemNameAndAction(i
                    , "回退界面", Material.DIAMOND,
                    (player
                            , item
                            , clickType
                            , action
                            , slot
                            , event) -> {
                        player.sendMessage("你点击了回退界面");
                        new NavGUI().open(player);
                    });
        }
    }

    public static EmailGUI getGUIFromPlayer(Player player) {
        return emails.get(player.getUniqueId());
    }

    public static void createEmailToPlayer(Player player) {
        if (emails.containsKey(player.getUniqueId())) return;
        EmailGUI gui = new EmailGUI();
        emails.put(player.getUniqueId(), gui);
    }

    public void addItemToEmail(ItemStack stack) {
        int i = GuiUtils.getFilterItemSlotCount(inventory);
        inventory.setItem(i, stack);
    }

    public void addItemsToEmail(List<ItemStack> stack) {
        for (ItemStack itemStack : stack) {
            addItemToEmail(itemStack);
        }
    }
}
