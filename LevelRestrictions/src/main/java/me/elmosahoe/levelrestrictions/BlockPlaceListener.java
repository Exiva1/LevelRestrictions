package me.elmosahoe.levelrestrictions;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final ConfigManager configManager;

    public BlockPlaceListener(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Material placedBlockMaterial = event.getBlock().getType();
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(event.getBlock());
        String blockId = null;

        if (customBlock != null) {
            // This is a custom block from ItemsAdder.
            blockId = customBlock.getId();
        }

        RestrictedItemType itemType = configManager.getItemTypeForMaterial(placedBlockMaterial);


        if (itemType != RestrictedItemType.OTHER) {
            return;
        }

        int requiredLevel;
        if (blockId != null) {
            requiredLevel = configManager.getRequiredLevelForCustomItem(blockId);
        } else {
            requiredLevel = configManager.getRequiredLevelForItem(placedBlockMaterial);
        }

        if (requiredLevel > 0 && event.getPlayer().getLevel() < requiredLevel) {
            event.setCancelled(true);
            MessageUtils.send(event.getPlayer(), MessageUtils.levelRequirementMessage(requiredLevel));
        }
    }
}