package me.elmosahoe.levelrestrictions;

import me.elmosahoe.levelrestrictions.commands.*;
import me.elmosahoe.levelrestrictions.listeners.ItemsAdderDataLoadListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelRestrictionsPlugin extends JavaPlugin {

    private ConfigManager configManager;
    private ItemsAdderDataLoadListener itemsAdderDataLoadListener;

    @Override
    public void onEnable() {
        // Initialize ConfigManager
        configManager = new ConfigManager(this);

        // Initialize and Register ItemsAdderDataLoadListener
        itemsAdderDataLoadListener = new ItemsAdderDataLoadListener();
        getServer().getPluginManager().registerEvents(itemsAdderDataLoadListener, this);

        // Register Commands
        getCommand("addrestriction").setExecutor(new AddRestrictionCommand(configManager));
        getCommand("removerestriction").setExecutor(new RemoveRestrictionCommand(configManager));
        getCommand("listrestrictions").setExecutor(new ListRestrictionsCommand(configManager));
        this.getCommand("testcustomitem").setExecutor(new TestCustomItemCommand());
        getCommand("testconfig").setExecutor(new TestConfigCommand(configManager));

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(configManager, this), this);
        getServer().getPluginManager().registerEvents(new ArmorEquipListener(configManager), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(configManager), this);

        getLogger().info("LevelRestrictionsPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LevelRestrictionsPlugin has been disabled!");
    }

    public boolean isItemsAdderDataLoaded() {
        return itemsAdderDataLoadListener.isDataLoaded();
    }
}