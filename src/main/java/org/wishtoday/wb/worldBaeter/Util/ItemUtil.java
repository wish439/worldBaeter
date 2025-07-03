package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.wishtoday.wb.worldBaeter.WorldBaeter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 物品工具类 - 提供设置物品名称的实用方法
 */
public class ItemUtil {
    @NotNull
    public static final NamespacedKey ITEM_UUID_KEY = NamespacedKey.fromString("item_uuid", WorldBaeter.getInstance());
    /**
     * 为已有物品设置显示名称
     * @param name   要设置的名称（Adventure文本组件）
     * @param stack  要修改的物品堆
     */
    public static void setName(Component name, ItemStack stack) {
        // 获取物品元数据
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return; // 安全校验
        // 设置显示名称
        meta.displayName(name);
        // 应用修改后的元数据
        stack.setItemMeta(meta);
    }
    /**
     * 创建新物品并设置名称（指定数量）
     * @param name   物品名称（Adventure文本组件）
     * @param item   物品材质
     * @param count  物品数量
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(Component name, Material item, int count) {
        // 创建基础物品堆
        ItemStack stack = new ItemStack(item, count);
        // 调用内部方法设置名称
        setName(name, stack);
        return stack;
    }
    /**
     * 创建新物品并设置名称（默认数量为1）
     * @param name   物品名称（Adventure文本组件）
     * @param item   物品材质
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(Component name, Material item) {
        // 创建基础物品堆（默认数量1）
        ItemStack stack = new ItemStack(item, 1);
        // 调用内部方法设置名称
        setName(name, stack);
        addUUIDToItem(stack);
        return stack;
    }
    /**
     * 创建新物品并设置名称（默认数量为1）
     * @param name   物品名称（Adventure文本组件）
     * @param item   物品材质
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(Component name, Material item,UUID uuid) {
        // 创建基础物品堆（默认数量1）
        ItemStack stack = new ItemStack(item, 1);
        // 调用内部方法设置名称
        setName(name, stack);
        addUUIDToItem(stack,uuid);
        return stack;
    }
    /**
     * 创建新物品并设置名称（使用字符串名称，默认数量为1）
     * @param name   物品名称（字符串）
     * @param item   物品材质
     * @return 创建并命名后的物品堆
     */
    public static ItemStack setName(String name, Material item) {
        // 将字符串转换为Adventure文本组件
        Component componentName = Component.text(name);
        // 创建基础物品堆（默认数量1）
        ItemStack stack = new ItemStack(item, 1);
        // 调用内部方法设置名称
        setName(componentName, stack);
        return stack;
    }
    @NotNull
    public static UUID addUUIDToItem(ItemStack stack, UUID uuid) {
        ItemMeta meta = stack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(ITEM_UUID_KEY, PersistentDataTypes.UUID)) return container.get(ITEM_UUID_KEY, PersistentDataTypes.UUID);
        container.set(ITEM_UUID_KEY, PersistentDataTypes.UUID , uuid);
        stack.setItemMeta(meta);
        return uuid;
    }
    public static boolean hasUUIDFromItem(ItemStack item) {
        boolean has = item.getItemMeta().getPersistentDataContainer().has(ITEM_UUID_KEY, PersistentDataTypes.UUID);
        UUID uuid = item.getItemMeta().getPersistentDataContainer().get(ITEM_UUID_KEY, PersistentDataTypes.UUID);
        return has && uuid != null;
    }
    public static UUID addUUIDToItem(ItemStack stack) {
        UUID uuid = UUID.randomUUID();
        return addUUIDToItem(stack, uuid);
    }
    @Nullable
    public static UUID getUUIDFromItem(ItemStack stack) {
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        if (container.has(ITEM_UUID_KEY, PersistentDataTypes.UUID)) {
            return container.get(ITEM_UUID_KEY, PersistentDataTypes.UUID);
        }
        return null;
    }
    public static void removeUUIDFromItem(ItemStack stack) {
        PersistentDataContainer container = stack.getItemMeta().getPersistentDataContainer();
        if (container.has(ITEM_UUID_KEY, PersistentDataTypes.UUID)) {
            container.remove(ITEM_UUID_KEY);
        }
    }
    @NotNull
    public static List<ItemStack> getItems(Inventory inventory, int... slot) {
        ArrayList<ItemStack> arrayList = new ArrayList<>();
        for (int i : slot) {
            ItemStack item = inventory.getItem(i);
            if (item == null) continue;
            arrayList.add(item);
        }
        return arrayList;
    }
    public static void setItems(Inventory inventory, ItemStack item, int... slot) {
        for (int i : slot) {
            inventory.setItem(i, item);
        }
    }
    public static void setChineseItemFromEnglish(ItemStack item) {
        TranslatableComponent translatable = Component.translatable(item.getType().translationKey());
        ItemMeta meta = item.getItemMeta();
        translatable = translatable.decoration(TextDecoration.ITALIC, false);
        meta.displayName(translatable);
        item.setItemMeta(meta);
    }
    public static void givePlayerItem(Player player, ItemStack item) {
        HashMap<Integer, ItemStack> map = player.getInventory().addItem(item);
        if (map.isEmpty()) return;
        List<ItemStack> items = new ArrayList<>(map.values());
        Location location = player.getEyeLocation().clone();
        location.setY(location.getY() - 0.30000001192092896D);
        for (ItemStack stack : items) {
            player.getWorld().dropItem(location, stack,  drop -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                float pitchRad = player.getPitch();
                float yawRad = player.getYaw();

                double sinPitch = Math.sin(pitchRad);
                double cosPitch = Math.cos(pitchRad);
                double sinYaw = Math.sin(yawRad);
                double cosYaw = Math.cos(yawRad);

                float randomAngle = random.nextFloat() * 6.2831855F; // 2π
                float randomMagnitude = 0.02F * random.nextFloat();

                double x = (cosYaw * cosPitch * 0.3F) + Math.sin(randomAngle) * randomMagnitude;
                double y = -sinPitch * 0.3F + 0.1F + (random.nextFloat() - random.nextFloat()) * 0.1F;
                double z = (-sinYaw * cosPitch * 0.3F) + Math.cos(randomAngle) * randomMagnitude;

                drop.setVelocity(new Vector(x,y,z));
                drop.setThrower(player.getUniqueId());
                drop.setPickupDelay(40);
            });
        }
    }
    public static void givePlayerItems(Player player, Inventory inventory, int... slot) {
        List<ItemStack> list = getItems(inventory, slot);
        for (ItemStack stack : list) {
            givePlayerItem(player, stack);
        }
    }

}
