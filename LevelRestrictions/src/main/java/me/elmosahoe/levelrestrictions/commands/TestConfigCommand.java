package me.elmosahoe.levelrestrictions.commands;

import me.elmosahoe.levelrestrictions.ConfigManager;
import me.elmosahoe.levelrestrictions.RestrictedItemType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestConfigCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public TestConfigCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /testconfig <customItemID>");
            return true;
        }

        String itemId = args[0];
        int level = configManager.getRequiredLevelForCustomItem(itemId);
        RestrictedItemType itemType = configManager.getItemTypeForCustomItem(itemId);

        sender.sendMessage("Level for " + itemId + ": " + level);
        sender.sendMessage("Type for " + itemId + ": " + itemType.name());

        return true;
    }
}