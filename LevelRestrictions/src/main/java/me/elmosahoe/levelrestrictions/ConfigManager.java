package me.elmosahoe.levelrestrictions;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final JavaPlugin plugin;
    private FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        initConfig();
    }

    private void initConfig() {
        config = plugin.getConfig();
        config.options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }

    public void addRestriction(RestrictedItem item) {
        String path = "restrictions." + item.getMaterial().name();
        config.set(path + ".type", item.getType().name());
        config.set(path + ".level", item.getRequiredLevel());
        saveConfig();
    }

    public void addCustomItemRestriction(String itemId, int requiredLevel, RestrictedItemType type) {
        String basePath = "customItems." + itemId; // Assuming itemId is in format "namespace:item_id"
        config.set(basePath + ".level", requiredLevel);
        config.set(basePath + ".type", type.name());
        saveConfig();
    }

    public void removeRestriction(Material material) {
        config.set("restrictions." + material.name(), null);
        saveConfig();
    }

    public void removeCustomItemRestriction(String itemId) {
        config.set("customItems." + itemId, null);
        saveConfig();
    }

    public List<RestrictedItem> getAllRestrictions() {
        List<RestrictedItem> restrictions = new ArrayList<>();
        if (config.getConfigurationSection("restrictions") != null) {
            for (String key : config.getConfigurationSection("restrictions").getKeys(false)) {
                Material material = Material.valueOf(key);
                RestrictedItemType type = RestrictedItemType.valueOf(config.getString("restrictions." + key + ".type"));
                int level = config.getInt("restrictions." + key + ".level");
                restrictions.add(new RestrictedItem(material, type, level));
            }
        }
        return restrictions;
    }

    public int getRequiredLevelForItem(Material material) {
        return config.getInt("restrictions." + material.name() + ".level", 0);
    }

    public int getRequiredLevelForCustomItem(String itemId) {
        return config.getInt("customItems." + itemId + ".level", 0); // Assuming itemId is in format "namespace:item_id"
    }

    public RestrictedItemType getItemTypeForCustomItem(String itemId) {
        if (config.contains("customItems." + itemId + ".type")) {
            return RestrictedItemType.valueOf(config.getString("customItems." + itemId + ".type"));
        }
        return null; // Default value if not found
    }

    public RestrictedItemType getItemTypeForMaterial(Material material) {
        if (config.getConfigurationSection("restrictions." + material.name()) != null) {
            return RestrictedItemType.valueOf(config.getString("restrictions." + material.name() + ".type"));
        }
        return null;
    }

    public boolean isCustomItem(String itemId) {
        return dev.lone.itemsadder.api.CustomStack.isInRegistry(itemId);
    }

    public void loadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}