package me.elmosahoe.levelrestrictions;

import org.bukkit.Material;

public class RestrictedItem {

    private final Material material;
    private final RestrictedItemType type;
    private final int requiredLevel;

    public RestrictedItem(Material material, RestrictedItemType type, int requiredLevel) {
        this.material = material;
        this.type = type;
        this.requiredLevel = requiredLevel;
    }

    public Material getMaterial() {
        return material;
    }

    public RestrictedItemType getType() {
        return type;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    // You can add more methods if needed, like setters if you want to change the properties after creation.
}