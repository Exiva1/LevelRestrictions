package me.elmosahoe.levelrestrictions.commands;

import me.elmosahoe.levelrestrictions.ConfigManager;
import me.elmosahoe.levelrestrictions.MessageUtils;
import me.elmosahoe.levelrestrictions.RestrictedItem;
import me.elmosahoe.levelrestrictions.RestrictedItemType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddRestrictionCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public AddRestrictionCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        if (args.length != 3) {
            MessageUtils.send((Player) sender, "&cUsage: /addrestriction <material/customItemId> <level> <type>");
            return true;
        }

        Material material = Material.getMaterial(args[0].toUpperCase());
        int level;

        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            MessageUtils.send((Player) sender, "&cInvalid level. Please enter a number.");
            return true;
        }

        RestrictedItemType type;
        try {
            type = RestrictedItemType.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            MessageUtils.send((Player) sender, "&cInvalid type. Valid types are: TOOL, ARMOR, OTHER.");
            return true;
        }

        if (material != null) {
            configManager.addRestriction(new RestrictedItem(material, type, level));
        } else {
            configManager.addCustomItemRestriction(args[0], level, type);
        }

        MessageUtils.send((Player) sender, "&aRestriction added successfully!");

        return true;
    }
}