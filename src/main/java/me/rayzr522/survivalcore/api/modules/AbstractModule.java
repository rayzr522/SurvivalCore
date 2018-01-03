package me.rayzr522.survivalcore.api.modules;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.storage.IStorageProvider;
import me.rayzr522.survivalcore.api.storage.impl.PlayerData;

import java.util.UUID;

public abstract class AbstractModule implements IModule {
    private SurvivalCore plugin;
    private IStorageProvider settings;
    private IStorageProvider storageProvider;

    private String _getCleanName() {
        return getName().trim().toLowerCase().replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    @Override
    public void onLoad(SurvivalCore core) {
        this.plugin = core;
        this.settings = core.getSettings().fork(_getCleanName());
        this.storageProvider = core.getStorageProvider().fork(_getCleanName());
    }

    @Override
    public SurvivalCore getPlugin() {
        return plugin;
    }

    @Override
    public IStorageProvider getSettings() {
        return settings;
    }

    @Override
    public IStorageProvider getStorage() {
        return storageProvider;
    }

    @Override
    public PlayerData getPlayerData(UUID player) {
        IStorageProvider playerData = plugin.getPlayerData().fork(player.toString());
        return new PlayerData(playerData.getOrCreateData(_getCleanName()).getRaw(), player, playerData);
    }
}
