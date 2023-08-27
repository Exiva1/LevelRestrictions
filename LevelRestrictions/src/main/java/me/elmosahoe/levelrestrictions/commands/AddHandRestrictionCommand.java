package me.elmosahoe.levelrestrictions.commands;

import dev.lone.itemsadder.api.CustomStack;
import me.elmosahoe.levelrestrictions.ConfigManager;
import me.elmosahoe.levelrestrictions.MessageUtils;
import me.elmosahoe.levelrestrictions.RestrictedItemType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddHandRestrictionCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public AddHandRestrictionCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null) {
            MessageUtils.send(player, "You need to hold an item in your hand to add a restriction for it.");
            return true;
        }

        CustomStack customStack = CustomStack.byItemStack(itemInHand);
        if (customStack == null) {
            MessageUtils.send(player, "The held item is not a recognized custom item.");
            return true;
        }

        String itemId = customStack.getId();
        configManager.addCustomItemRestriction(itemId, 5, RestrictedItemType.TOOL);
        MessageUtils.send(player, "Added restriction for the held item.");

        return true;
    }
}

