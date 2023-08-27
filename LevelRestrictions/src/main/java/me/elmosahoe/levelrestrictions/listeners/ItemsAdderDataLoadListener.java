package me.elmosahoe.levelrestrictions.listeners;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemsAdderDataLoadListener implements Listener {

    private boolean isDataLoaded = false;

    @EventHandler
    public void onDataLoad(ItemsAdderLoadDataEvent event) {
        isDataLoaded = true;
    }

    public boolean isDataLoaded() {
        return isDataLoaded;
    }
}