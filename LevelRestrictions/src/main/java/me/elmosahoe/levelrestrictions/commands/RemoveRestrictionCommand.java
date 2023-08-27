package me.elmosahoe.levelrestrictions.commands;

import me.elmosahoe.levelrestrictions.ConfigManager;
import me.elmosahoe.levelrestrictions.MessageUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveRestrictionCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public RemoveRestrictionCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        if (args.length != 1) {
            MessageUtils.send((Player) sender, "&cUsage: /removerestriction <material/itemId>");
            return true;
        }

        Material material = Material.getMaterial(args[0].toUpperCase());

        if (material != null) {
            configManager.removeRestriction(material);
            MessageUtils.send((Player) sender, "&aRestriction for material " + material.name() + " removed successfully!");
        } else if (configManager.isCustomItem(args[0])) {
            configManager.removeCustomItemRestriction(args[0]);
            MessageUtils.send((Player) sender, "&aRestriction for custom item " + args[0] + " removed successfully!");
        } else {
            MessageUtils.send((Player) sender, "&cInvalid material or custom item ID.");
        }

        return true;
    }
}