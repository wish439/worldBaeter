package org.wishtoday.wb.worldBaeter.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.wishtoday.wb.Impls.Annotation;

import java.util.List;


/**
 * 市场物品实体类 - 表示一个市场交易条目
 * <p>
 * 用于封装玩家在市场出售物品的交易信息
 */

public class MarketItemData {

    @Annotation("此交易的创建者数据")
    private PlayerData player;
    @Annotation("此交易的出售品列表")
    private List<ItemStack> item;

    @Annotation("此交易需要的物品列表")
    private List<ItemStack> needItem;

    @Annotation("此交易的编号")
    private int tag;

    public MarketItemData(List<ItemStack> item, List<ItemStack> needItem, PlayerData player) {
        this.item = item;
        this.needItem = needItem;
        this.player = player;
    }
    public MarketItemData(List<ItemStack> item, List<ItemStack> needItem, Player player) {
        this.item = item;
        this.needItem = needItem;
        this.player = new PlayerData(player);
    }

    public List<ItemStack> getItem() {
        return item;
    }

    public void setItem(List<ItemStack> item) {
        this.item = item;
    }

    public List<ItemStack> getNeedItem() {
        return needItem;
    }

    public void setNeedItem(List<ItemStack> needItem) {
        this.needItem = needItem;
    }
    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }
}