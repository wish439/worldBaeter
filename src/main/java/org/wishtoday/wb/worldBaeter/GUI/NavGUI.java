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

// 主导航菜单GUI，提供核心功能入口
public class NavGUI {
    // 存储菜单项文本与点击动作的映射关系（Key: 菜单项文本组件, Value: 点击动作）
    private static Map<Component, ClickAction> listeners = new HashMap<>();
    // 存储菜单槽位与对应物品的映射（Key: 槽位索引, Value: 物品堆）
    private static Map<Integer, ItemStack> addItems = new HashMap<>();
    // 固定GUI标题
    private static final Component GUI_TITLE = Component.text("World Baeter");
    // 创建基础GUI（使用工具类生成带玻璃边框的库存）
    private static Inventory inventory = GuiUtils.createInventoryWithGlass(GuiUtils.SMALLCHESTSIZE, GUI_TITLE);
    // 获取GUI库存大小
    public static int getInventorySize() {
        return inventory.getSize();
    }
    // 为玩家打开GUI
    public static void open(Player player) {
        player.closeInventory();// 先关闭玩家当前打开的库存
        setInventoryItemFromMap();// 将预定义的物品加载到GUI中
        player.openInventory(inventory);// 打开本GUI
    }
    // 获取GUI标题
    public static Component getTitle() {
        return GUI_TITLE;
    }
    // 获取GUI库存实例
    public static Inventory getInventory() {
        return inventory;
    }
    // 获取所有预定义的菜单项
    public static Map<Integer, ItemStack> getAddItems() {
        return addItems;
    }
    /**
     * 添加菜单项及其点击动作
     * @param slot    物品放置的槽位
     * @param name    物品显示名称
     * @param material 物品材质
     * @param action  点击触发的动作
     */
    public static void addItemNameAndAction(
            int slot
            , String name
            , Material material
            , ClickAction action) {
        TextComponent text = Component.text(name);// 创建文本组件
        // 通过工具类创建带名称的物品，并存入映射
        addItems.put(slot, ItemUtil.setName(text, material));
        // 关联文本组件和点击动作（用于后续事件触发）
        listeners.put(text, action);
    }
    // 获取所有监听器（文本->动作映射）
    public static Map<Component, ClickAction> getListeners() {
        return listeners;
    }
    // 根据文本组件获取对应的点击动作
    public static ClickAction getAction(Component component) {
        return listeners.get(component);
    }
    // 检查组件是否是菜单项（是否已注册监听）
    public static boolean isGUIItem(Component component) {
        return listeners.containsKey(component);
    }
    // 执行菜单项对应的点击动作
    public static void runAction(Component component
            , Player player
            , ItemStack item
            , ClickType type
            , InventoryAction inventoryAction) {
        getAction(component).click(player,item,type,inventoryAction);
    }
    /**
     * 初始化菜单项（通常在服务器启动时调用）
     * 定义所有导航按钮的位置、名称、材质和点击行为
     */
    // 可能需要在服务器开启时调用
    public static void initializeInventoryItem() {
        // 出售物品按钮（槽位10）
        addItemNameAndAction(
                10
                , "出售"
                , Material.DIAMOND
                ,(player
                        , item
                        , clickType
                        , action) -> {
                    player.sendMessage(Component.text("你点击了\"出售\""));
                    SellItemGUI.openInventoryToPlayer(player);
                });
        // 浏览市场按钮（槽位11）
        addItemNameAndAction(
                11,
                "浏览商品"
                , Material.IRON_INGOT,
                (player
                        , item
                        , clickType
                        , action) -> {
                    player.sendMessage(Component.text("你点击了\"浏览商品\""));
                });
        // 查看当前出售物品按钮（槽位12）
        addItemNameAndAction(
                12,
                "我正在出售的",
                Material.CHEST,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"我正在出售的商品\""));
                });
        // 取回物品按钮（槽位13）
        addItemNameAndAction(
                13,
                "取回物品",
                Material.HOPPER,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"取回物品\""));
                });
        // 购买历史按钮（槽位14，点击后关闭GUI）
        addItemNameAndAction(
                14,
                "购买历史",
                Material.BARRIER,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"购买历史\""));
                    player.closeInventory();
                }
        );
        // 出售历史按钮（槽位15，点击后关闭GUI）
        addItemNameAndAction(
                15,
                "出售历史",
                Material.BARRIER,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"出售历史\""));
                    player.closeInventory();
                }
        );
        // 邮箱/交易物领取按钮（槽位16）
        addItemNameAndAction(
                16,
                "邮箱",
                Material.ENDER_CHEST,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"取出交易物\""));
                }
        );
        // 由于直接定位市场页数功能（槽位21）
        addItemNameAndAction(
                21,
                "点击跳转到指定页",
                Material.PAPER,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"转到指定页\""));
                }
        );
        // 退出按钮（槽位22，点击后关闭GUI）
        addItemNameAndAction(
                22,
                "退出",
                Material.OAK_DOOR,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"退出\""));
                    player.closeInventory();
                }
        );
        // 用于查看指定玩家出售列表按钮（槽位23）
        addItemNameAndAction(
                23,
                "玩家出售列表",
                Material.PLAYER_HEAD,
                (player, item, clickType, action) -> {
                    player.sendMessage(Component.text("你点击了\"玩家出售列表\""));
                }
        );
    }
    // 将预定义的菜单项加载到GUI库存中
    public static void setInventoryItemFromMap() {
        // 遍历所有预注册的菜单项
        for (Map.Entry<Integer, ItemStack> entry : addItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

    }
}
