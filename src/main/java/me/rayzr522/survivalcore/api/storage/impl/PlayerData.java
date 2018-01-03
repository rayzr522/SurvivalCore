package me.rayzr522.survivalcore.api.storage.impl;

import me.rayzr522.survivalcore.api.storage.Hunk;
import me.rayzr522.survivalcore.api.storage.IStorageProvider;

import java.util.Map;
import java.util.UUID;

public class PlayerData extends Hunk {
    private final UUID id;
    private final IStorageProvider storageProvider;

    public PlayerData(Map<String, Object> data, UUID id, IStorageProvider storageProvider) {
        super(data);
        this.id = id;
        this.storageProvider = storageProvider;
    }

    /**
     * @return The {@link UUID} of this player.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Saves the {@link PlayerData}, saving the data-source.
     */
    public void save() {
        // uhm, oh, okay, thought I needed this... apparently my data system is even cooler than I thought, it even did things I didn't know it would do!
        // storageProvider.setData(id.toString(), this);
        storageProvider.save();
    }
}
