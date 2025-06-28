package org.wishtoday.wb.worldBaeter.Util;

import org.bukkit.inventory.ItemStack;

/**
 * 市场物品实体类 - 表示一个市场交易条目
 * <p>
 * 用于封装玩家在市场出售物品的交易信息
 */
public class MarketItems {
    // 出售的物品（玩家提供的商品）
    private ItemStack item;

    // 交易所需的物品（买家需要支付的物品）
    private ItemStack needItem;

    /**
     * 构造函数 - 创建市场物品条目
     * @param item      出售的物品（不能为null）
     * @param needItem  交易所需的物品（不能为null）
     */
    public MarketItems(ItemStack item, ItemStack needItem) {
        this.item = item;
        this.needItem = needItem;
    }

    /**
     * 获取出售的物品
     * @return 出售的物品堆
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * 设置出售的物品
     * @param item 新的出售物品
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }

    /**
     * 获取交易所需的物品
     * @return 交易所需的物品堆
     */
    public ItemStack getNeedItem() {
        return needItem;
    }

    /**
     * 设置交易所需的物品
     * @param needItem 新的交易要求物品
     */
    public void setNeedItem(ItemStack needItem) {
        this.needItem = needItem;
    }
}