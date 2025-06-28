package org.wishtoday.wb.worldBaeter.Util;

import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    private String name;
    public PlayerData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
    public PlayerData(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PlayerData data = (PlayerData) object;
        return Objects.equals(uuid, data.uuid) && Objects.equals(name, data.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
