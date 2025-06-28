package org.wishtoday.wb.worldBaeter.Util;

import org.bukkit.inventory.ItemStack;

public class MarketItems {
    private ItemStack item;
    private ItemStack needItem;

    public MarketItems(ItemStack item, ItemStack needItem) {
        this.item = item;
        this.needItem = needItem;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getNeedItem() {
        return needItem;
    }

    public void setNeedItem(ItemStack needItem) {
        this.needItem = needItem;
    }
}
