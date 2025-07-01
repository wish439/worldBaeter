package org.wishtoday.wb.worldBaeter.GUI;


import net.kyori.adventure.text.Component;
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

    private static final TextColor[] COLOR_COLORED_27983352722183021697 = {
            TextColor.color(0x3498db),
            TextColor.color(0x9b59b6)
    };

    private static String colored_27983352722183021697() {
        Component comp = TextGradient.createGradient("全服市场", COLOR_COLORED_27983352722183021697)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    private static final TextColor[] COLOR_COLORED_251052749122312209862180630340 = {
            TextColor.color(0x1abc9c),
            TextColor.color(0x2ecc71)
    };

    private static String colored_251052749122312209862180630340() {
        Component comp = TextGradient.createGradient("卖家中心", COLOR_COLORED_251052749122312209862180630340)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    private static final TextColor[] COLOR_COLORED_21462222382928921697 = {
            TextColor.color(0xf39c12),
            TextColor.color(0xe67e22)
    };

    private static String colored_21462222382928921697() {
        Component comp = TextGradient.createGradient("取回交易物品", COLOR_COLORED_21462222382928921697)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    private static final TextColor[] COLOR_COLORED_3703831665 = {
            TextColor.color(0x9b59b6),
            TextColor.color(0xe74c3c)
    };

    private static String colored_3703831665() {
        Component comp = TextGradient.createGradient("领取交易货物", COLOR_COLORED_3703831665)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    private static final TextColor[] COLOR_COLORED_2885720987363393671621040253512345039029 = {
            TextColor.color(0x95a5a6),
            TextColor.color(0x7f8c8d)
    };

    private static String colored_2885720987363393671621040253512345039029() {
        Component comp = TextGradient.createGradient("点击跳转到指定页", COLOR_COLORED_2885720987363393671621040253512345039029)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    private static final TextColor[] COLOR_COLORED_3686420986 = {
            TextColor.color(0xc0392b),
            TextColor.color(0xe74c3c)
    };

    private static String colored_3686420986() {
        Component comp = TextGradient.createGradient("退出", COLOR_COLORED_3686420986)
                .decoration(TextDecoration.BOLD, true);
        return LEGACY.serialize(comp);
    }

    private static final TextColor[] COLOR_COLORED_296092347820986218062101534920 = {
            TextColor.color(0x16a085),
            TextColor.color(0x27ae60)
    };

    private static String colored_296092347820986218062101534920() {
        Component comp = TextGradient.createGradient("查看玩家出售列表", COLOR_COLORED_296092347820986218062101534920)
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
        // 浏览市场按钮（槽位11）
        addItemNameAndAction(
                11,
                colored_27983352722183021697()
                , Material.IRON_INGOT,
                (player
                        , item
                        , clickType
                        , action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"全服市场\""));

                    MarketGUI.getInstance().open(player,0);
                });

        // 查看当前出售物品按钮（槽位12）
        addItemNameAndAction(
                12,
                colored_251052749122312209862180630340(),
                Material.CHEST,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"卖家中心\""));
                });

        // 取回物品按钮（槽位13）
        addItemNameAndAction(
                13,
                colored_21462222382928921697(),
                Material.HOPPER,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"取回交易物品\""));
                });

        // 购买历史按钮（槽位14，点击后关闭GUI）
        addItemNameAndAction(
                14,
                "购买历史",
                Material.BARRIER,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"购买历史\""));
                    player.closeInventory();
                }
        );
        // 出售历史按钮（槽位15，点击后关闭GUI）
        addItemNameAndAction(
                15,
                "出售历史",
                Material.BARRIER,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"出售历史\""));
                    player.closeInventory();
                }
        );

        // 邮箱/交易物领取按钮（槽位16）
        addItemNameAndAction(
                16,
                colored_3703831665(),
                Material.ENDER_CHEST,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"领取交易货物\""));
                }
        );
        // 由于直接定位市场页数功能（槽位21）
        addItemNameAndAction(
                21,
                colored_2885720987363393671621040253512345039029(),
                Material.PAPER,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"转到指定页\""));
                }
        );

        // 退出按钮（槽位22，点击后关闭GUI）
        addItemNameAndAction(
                22,
                colored_3686420986(),
                Material.OAK_DOOR,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"退出\""));
                    player.closeInventory();
                }
        );

        // 用于查看指定玩家出售列表按钮（槽位23）
        addItemNameAndAction(
                23,
                colored_296092347820986218062101534920(),
                Material.PLAYER_HEAD,
                (player, item, clickType, action, __slot, event) -> {
                    player.sendMessage(Component.text("你点击了\"玩家出售列表\""));
                }
        );
    }
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
