package org.wishtoday.wb.worldBaeter.Util;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.wishtoday.wb.Impls.Annotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 市场物品实体类 - 表示一个市场交易条目
 * <p>
 * 用于封装玩家在市场出售物品的交易信息
 */

public class MarketItemData implements ConfigurationSerializable {

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
        tag = 0;
    }
    public MarketItemData(List<ItemStack> item, List<ItemStack> needItem, Player player) {
        this.item = item;
        this.needItem = needItem;
        this.player = new PlayerData(player);
        tag = 0;
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

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("player", player.serialize());
        map.put("item", serializeItemList(item));
        map.put("needItem", serializeItemList(needItem));
        map.put("tag", tag);
        return map;
    }
    public static MarketItemData deserialize(Map<String, Object> args) {
        PlayerData player = PlayerData.deserialize((Map<String, Object>) args.get("player"));
        List<ItemStack> item = deserializeItemList((List<Map<String, Object>>) args.get("item"));
        List<ItemStack> needItem = deserializeItemList((List<Map<String, Object>>) args.get("needItem"));
        int tag = (Integer) args.get("tag");
        return new MarketItemData(item, needItem,player);
    }
    private List<Map<String, Object>> serializeItemList(List<ItemStack> items) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ItemStack item : items) {
            result.add(item.serialize());
        }
        return result;
    }

    // 反序列化物品列表
    private static List<ItemStack> deserializeItemList(List<Map<String, Object>> data) {
        List<ItemStack> result = new ArrayList<>();
        for (Map<String, Object> itemData : data) {
            result.add(ItemStack.deserialize(itemData));
        }
        return result;
    }

}