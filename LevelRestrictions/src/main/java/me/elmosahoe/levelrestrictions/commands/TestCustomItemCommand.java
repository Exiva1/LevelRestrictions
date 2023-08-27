package me.elmosahoe.levelrestrictions.commands;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCustomItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /testcustomitem <itemId>");
            return true;
        }

        boolean isCustom = CustomStack.isInRegistry(args[0]);
        if (isCustom) {
            sender.sendMessage(args[0] + " is a valid custom item!");
        } else {
            sender.sendMessage(args[0] + " is NOT a valid custom item.");
        }

        return true;
    }
}