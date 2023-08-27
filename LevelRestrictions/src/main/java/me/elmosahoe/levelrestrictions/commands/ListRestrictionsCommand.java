package me.elmosahoe.levelrestrictions.commands;

import me.elmosahoe.levelrestrictions.ConfigManager;
import me.elmosahoe.levelrestrictions.MessageUtils;
import me.elmosahoe.levelrestrictions.RestrictedItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ListRestrictionsCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public ListRestrictionsCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        List<RestrictedItem> restrictions = configManager.getAllRestrictions();

        if (restrictions.isEmpty()) {
            MessageUtils.send((Player) sender, "&cThere are no restrictions currently set.");
            return true;
        }

        MessageUtils.send((Player) sender, "&aCurrent Restrictions:");
        for (RestrictedItem item : restrictions) {
            MessageUtils.send((Player) sender, "&b" + item.getMaterial().name() + " (" + item.getType().name() + "): Level " + item.getRequiredLevel());
        }

        return true;
    }
}
