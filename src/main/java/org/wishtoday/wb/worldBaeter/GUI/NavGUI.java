package org.wishtoday.wb.worldBaeter.GUI;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.wishtoday.wb.worldBaeter.Util.GuiUtils;
import org.wishtoday.wb.worldBaeter.Util.TextGradient;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;


// 主导航菜单GUI，提供核心功能入口
public class NavGUI extends BaseGUI {

    // 固定GUI标题
    private static final Component GUI_TITLE = TextGradient.createGradient(
            "导航菜单",
            new TextColor[]{
                    TextColor.color(0x4facfe),   // 天蓝
                    TextColor.color(0x8e54e9)    // 紫罗兰
            }
    ).decoration(TextDecoration.BOLD, true);

    // 渐变颜色：绿色→金色
    private static final TextColor[] SELL_COLORS = {
            TextColor.color(0x2ecc71),
            TextColor.color(0xf1c40f)

    };

    // 渐变颜色：蓝 → 紫（全服市场按钮）
    private static final TextColor[] MARKET_COLORS = {
            TextColor.color(0x3498db),  // 蓝
            TextColor.color(0x9b59b6)   // 紫
    };

    private static String marketColored() {
        Component comp = TextGradient.createGradient("全服市场", MARKET_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // 渐变颜色：湖绿 → 草绿（卖家中心按钮）
    private static final TextColor[] SELL_CENTER_COLORS = {
            TextColor.color(0x1abc9c),  // 湖绿
            TextColor.color(0x2ecc71)   // 草绿
    };

    private static String sellCenterColored() {
        Component comp = TextGradient.createGradient("卖家中心", SELL_CENTER_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // 渐变颜色：橙黄 → 橙（取回交易物品按钮）
    private static final TextColor[] TAKE_BACK_COLORS = {
            TextColor.color(0xf39c12),  // 橙黄
            TextColor.color(0xe67e22)   // 橙
    };

    private static String takeBackColored() {
        Component comp = TextGradient.createGradient("取回交易物品", TAKE_BACK_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // 渐变颜色：紫 → 红（领取交易货物按钮）
    private static final TextColor[] RECEIVE_GOODS_COLORS = {
            TextColor.color(0x9b59b6),  // 紫色
            TextColor.color(0xe74c3c)   // 红色
    };

    private static String receiveGoodsColored() {
        Component comp = TextGradient.createGradient("领取交易货物", RECEIVE_GOODS_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // 渐变颜色：浅灰 → 深灰（跳页按钮）
    private static final TextColor[] GOTO_PAGE_COLORS = {
            TextColor.color(0x95a5a6),  // 浅灰
            TextColor.color(0x7f8c8d)   // 深灰
    };

    private static String gotoPageColored() {
        Component comp = TextGradient.createGradient("点击跳转到指定页", GOTO_PAGE_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // 渐变颜色：深红 → 鲜红（退出按钮）
    private static final TextColor[] EXIT_BUTTON_COLORS = {
            TextColor.color(0xc0392b),
            TextColor.color(0xe74c3c)
    };

    private static String exitButtonColored() {
        Component comp = TextGradient.createGradient("退出", EXIT_BUTTON_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // 渐变颜色：青绿 → 深绿
    private static final TextColor[] VIEW_PLAYER_COLORS = {
            TextColor.color(0x16a085),
            TextColor.color(0x27ae60)
    };

    private static String viewPlayerColored() {
        Component comp = TextGradient.createGradient("查看玩家出售列表", VIEW_PLAYER_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    // Legacy § 颜色码序列化器

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()

            .hexColors()

            .character('§')

            .build();

    private static String colored() {

        Component comp = TextGradient.createGradient("物品交换", SELL_COLORS)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);

    }

    // 创建基础GUI（使用工具类生成带玻璃边框的库存）
    private final Inventory inventory = GuiUtils.createInventoryWithGlass(GuiUtils.SMALLCHESTSIZE, GUI_TITLE);
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
                , colored()
                , Material.DIAMOND
                , (player
                        , item
                        , clickType
                        , action
                        , __slot
                        , event) -> {
                    player.sendMessage(Component.text("你点击了\"物品交换\""));
                    SellItemGUI gui = new SellItemGUI();
                    gui.initializeItems();
                    gui.open(player);
                });
        // 浏览市场按钮（槽位 11）
        addItemNameAndAction(
                11,
                marketColored(),
                Material.IRON_INGOT,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true);    // 防止拖动按钮
                    player.sendMessage(Component.text("你点击了\"全服市场\""));
                    MarketGUI.getInstance().open(player, 0);
                }
        );

        // 查看当前出售物品按钮（槽位 12）
        addItemNameAndAction(
                12,
                sellCenterColored(),
                Material.CHEST,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true);       // 防止拖动按钮
                    player.sendMessage(Component.text("你点击了\"卖家中心\""));
                }
        );

        // 取回物品按钮（槽位 13）
        addItemNameAndAction(
                13,
                takeBackColored(),
                Material.HOPPER,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true);                       // 避免拖拽
                    player.sendMessage(Component.text("你点击了\"取回交易物品\""));
                }
        );

        // 购买历史按钮（槽位 14）
        addItemNameAndAction(
                14,
                "购买历史",
                Material.BARRIER,
                (player, item, clickType, action, __slot, event) -> {
                    // 阻止玩家把按钮拖出背包
                    event.setCancelled(true);
                    // 关闭当前 GUI
                    player.closeInventory();
                    // 发送提示信息 —— 功能还在开发中
                    player.sendMessage(Component.text("购买历史功能正在开发中，敬请期待！",
                            NamedTextColor.YELLOW));
                });
        // 出售历史按钮（槽位 15）
        addItemNameAndAction(
                15,
                "出售历史",
                Material.BARRIER,
                (player, item, clickType, action, __slot, event) -> {
                    // 阻止玩家把按钮拖出背包
                    event.setCancelled(true);
                    // 关闭当前 GUI
                    player.closeInventory();
                    // 发送提示信息 —— 功能还在开发中
                    player.sendMessage(Component.text("出售历史功能正在开发中，敬请期待！",
                            NamedTextColor.YELLOW));
                }
        );

        // 领取交易货物按钮（槽位 16）
        addItemNameAndAction(
                16,
                receiveGoodsColored(),
                Material.CHEST,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true);
                    player.sendMessage(Component.text("你点击了领取交易货物"));
                }
        );
        // 跳页按钮（槽位 21）
        addItemNameAndAction(
                21,
                gotoPageColored(),
                Material.MAP,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true);
                    player.sendMessage(Component.text("你点击了跳转页按钮"));
                }
        );

        // 退出按钮（槽位 22，点击后关闭 GUI）
        addItemNameAndAction(
                22,
                exitButtonColored(),
                Material.OAK_DOOR,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true); // 避免拖拽
                    player.closeInventory();  // 关闭 GUI
                    player.sendMessage(Component.text("你已退出界面"));
                }
        );

        // 用于查看指定玩家出售列表按钮（槽位 23）
        addItemNameAndAction(
                23,
                viewPlayerColored(),
                Material.PLAYER_HEAD,
                (player, item, clickType, action, __slot, event) -> {
                    event.setCancelled(true); // 防止拖动按钮
                    player.sendMessage(Component.text("你点击了\"玩家出售列表\""));
                }
        );
    }
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
