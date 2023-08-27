package me.elmosahoe.levelrestrictions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static boolean isTool(ItemStack item) {
        return RestrictedItemType.getType(item.getType()) == RestrictedItemType.TOOL;
    }

    public static boolean isWeapon(ItemStack item) {
        return RestrictedItemType.getType(item.getType()) == RestrictedItemType.WEAPON;
    }

    public static boolean isArmor(ItemStack item) {
        return RestrictedItemType.getType(item.getType()) == RestrictedItemType.ARMOR;
    }

    // If you want to check for ItemsAdder compatibility or custom items, you can add methods here.
    // Example (pseudo-code):
    // public static boolean isCustomItem(ItemStack item) {
    //     return ItemsAdder.isCustomItem(item);
    // }
}