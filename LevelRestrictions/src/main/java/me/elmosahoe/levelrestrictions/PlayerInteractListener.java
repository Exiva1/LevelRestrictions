package me.elmosahoe.levelrestrictions;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final ConfigManager configManager;
    private final LevelRestrictionsPlugin mainPlugin;

    public PlayerInteractListener(ConfigManager configManager, LevelRestrictionsPlugin mainPlugin) {
        this.configManager = configManager;
        this.mainPlugin = mainPlugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!mainPlugin.isItemsAdderDataLoaded()) {
            return; // If ItemsAdder data isn't loaded, we don't process the event.
        }

        ItemStack item = event.getItem();
        if (item == null) return;

        CustomStack customItem = CustomStack.byItemStack(item);
        Material material;
        String itemId = null;

        if (customItem != null) {
            // This is a custom item from ItemsAdder.
            itemId = customItem.getId();
            material = item.getType(); // This will be the underlying Minecraft material for the custom item.
        } else {
            // This is a regular Minecraft item.
            material = item.getType();
        }

        RestrictedItemType itemType = configManager.getItemTypeForMaterial(material);

        // If the item is of type ARMOR, we exclude it from this listener
        if (itemType == RestrictedItemType.ARMOR) {
            return;
        }

        int requiredLevel;
        if (itemId != null) {
            requiredLevel = configManager.getRequiredLevelForCustomItem(itemId);
        } else {
            requiredLevel = configManager.getRequiredLevelForItem(material);
        }

        if (requiredLevel > 0) {
            int playerLevel = event.getPlayer().getLevel();

            if (playerLevel < requiredLevel) {
                event.setCancelled(true);
                MessageUtils.send(event.getPlayer(), MessageUtils.levelRequirementMessage(requiredLevel));
            }
        }
    }
}