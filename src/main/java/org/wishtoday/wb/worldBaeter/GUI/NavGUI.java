package org.wishtoday.wb.worldBaeter.GUI;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;


// 主导航菜单GUI，提供核心功能入口
public class NavGUI extends BaseGUI {
    // 固定GUI标题
    private static final Component GUI_TITLE = Component.text("World Baeter");
    // 创建基础GUI（使用工具类生成带玻璃边框的库存）
    private Inventory inventory = GuiUtils.createInventoryWithGlass(GuiUtils.SMALLCHESTSIZE, GUI_TITLE);

    public NavGUI() {
        super(GUI_TITLE, GuiUtils.SMALLCHESTSIZE);
    }

    @Override
    public Inventory createInitialInventory(int size, Component title) {
        return GuiUtils.createInventoryWithGlass(size, title);
    }

    @Override
    protected void initializeItems() {
        // 出售物品按钮（槽位10）
        addItemNameAndAction(
                10
                , "出售"
                , Material.DIAMOND
                , (player
                        , item
                        , clickType
                        , action
                        , slot
                        , event) -> {
                    player.sendMessage(Component.text("你点击了\"出售\""));
                    SellItemGUI gui = new SellItemGUI();
                    gui.initializeItems();
                    gui.open(player);
                });
        // 浏览市场按钮（槽位11）
        addItemNameAndAction(
                11,
                "浏览商品"
                , Material.IRON_INGOT,
                (player
                        , item
                        , clickType
                        , action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"浏览商品\""));
                    MarketGUI.getInstance().open(player,0);
                });
        // 查看当前出售物品按钮（槽位12）
        addItemNameAndAction(
                12,
                "我正在出售的",
                Material.CHEST,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"我正在出售的商品\""));
                });
        // 取回物品按钮（槽位13）
        addItemNameAndAction(
                13,
                "取回物品",
                Material.HOPPER,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"取回物品\""));
                });
        // 购买历史按钮（槽位14，点击后关闭GUI）
        addItemNameAndAction(
                14,
                "购买历史",
                Material.BARRIER,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"购买历史\""));
                    player.closeInventory();
                }
        );
        // 出售历史按钮（槽位15，点击后关闭GUI）
        addItemNameAndAction(
                15,
                "出售历史",
                Material.BARRIER,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"出售历史\""));
                    player.closeInventory();
                }
        );
        // 邮箱/交易物领取按钮（槽位16）
        addItemNameAndAction(
                16,
                "邮箱",
                Material.ENDER_CHEST,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"取出交易物\""));
                }
        );
        // 由于直接定位市场页数功能（槽位21）
        addItemNameAndAction(
                21,
                "点击跳转到指定页",
                Material.PAPER,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"转到指定页\""));
                }
        );
        // 退出按钮（槽位22，点击后关闭GUI）
        addItemNameAndAction(
                22,
                "退出",
                Material.OAK_DOOR,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"退出\""));
                    player.closeInventory();
                }
        );
        // 用于查看指定玩家出售列表按钮（槽位23）
        addItemNameAndAction(
                23,
                "玩家出售列表",
                Material.PLAYER_HEAD,
                (player, item, clickType, action, slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"玩家出售列表\""));
                }
        );
    }
}
