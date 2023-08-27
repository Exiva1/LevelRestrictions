package me.elmosahoe.levelrestrictions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.BLUE + "LevelRestrictions" + ChatColor.GRAY + "] ";

    public static void send(Player player, String message) {
        player.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&', message));
    }

    public static String levelRequirementMessage(int requiredLevel) {
        return ChatColor.GRAY + "You need level " + ChatColor.GOLD + requiredLevel + ChatColor.GRAY + " to use this item.";
    }
}