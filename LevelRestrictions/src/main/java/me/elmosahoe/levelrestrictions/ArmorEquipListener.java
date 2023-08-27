package me.elmosahoe.levelrestrictions;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class ArmorEquipListener implements Listener {

    private final ConfigManager configManager;

    public ArmorEquipListener(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() != SlotType.ARMOR && !event.isShiftClick()) return;

        // Check if the clicked inventory is the player's inventory
        if (!event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;

        ItemStack cursorItem = event.getCursor();

        if (event.isShiftClick()) {
            cursorItem = event.getCurrentItem();
        }

        if (cursorItem == null) return;

        CustomStack customStack = CustomStack.byItemStack(cursorItem);
        String customItemId = (customStack != null) ? customStack.getId() : null;

        RestrictedItemType itemType = configManager.getItemTypeForMaterial(cursorItem.getType());

        int requiredLevel;
        if (customItemId != null) {
            requiredLevel = configManager.getRequiredLevelForCustomItem(customItemId);
        } else {
            requiredLevel = configManager.getRequiredLevelForItem(cursorItem.getType());
        }

        if (requiredLevel > 0) {
            int playerLevel = ((Player) event.getWhoClicked()).getLevel();

            // Check if the action is placing an item into an armor slot
            if ((event.getSlotType() == SlotType.ARMOR && event.getRawSlot() >= 5) ||
                    (event.getAction() == InventoryAction.PLACE_ONE || event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {
                if (playerLevel < requiredLevel) {
                    event.setCancelled(true);
                    MessageUtils.send((Player) event.getWhoClicked(), MessageUtils.levelRequirementMessage(requiredLevel));
                }
            }
        }
    }

    @EventHandler
    public void onRightClickEquip(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getItem() == null) return;

        ItemStack item = event.getItem();
        CustomStack customStack = CustomStack.byItemStack(item);
        String customItemId = (customStack != null) ? customStack.getId() : null;

        RestrictedItemType itemType = configManager.getItemTypeForMaterial(item.getType());

        int requiredLevel;
        if (customItemId != null) {
            requiredLevel = configManager.getRequiredLevelForCustomItem(customItemId);
        } else {
            requiredLevel = configManager.getRequiredLevelForItem(item.getType());
        }

        if (requiredLevel > 0 && event.getPlayer().getLevel() < requiredLevel) {
            event.setCancelled(true);
            MessageUtils.send(event.getPlayer(), MessageUtils.levelRequirementMessage(requiredLevel));
        }
    }
}