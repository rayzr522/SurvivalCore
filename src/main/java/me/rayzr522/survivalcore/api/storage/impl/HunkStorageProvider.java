package me.rayzr522.survivalcore.api.storage.impl;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.storage.Hunk;
import me.rayzr522.survivalcore.api.storage.IStorageProvider;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HunkStorageProvider implements IStorageProvider {
    private String prefix = "";

    private Hunk hunk;
    private String key;
    private IStorageProvider parent;

    public HunkStorageProvider(Hunk hunk, String key, IStorageProvider parent) {
        Objects.requireNonNull(hunk, "hunk cannot be null!");
        Objects.requireNonNull(key, "key cannot be null!");
        Objects.requireNonNull(parent, "parent cannot be null!");

        this.hunk = hunk;
        this.key = key;
        this.parent = parent;
    }

    @Override
    public void load(SurvivalCore plugin) {
        // NOT NEEDED
    }

    @Override
    public void reload() {
        // TODO: Should I not be calling parent.reload()? Hmm...
        parent.reload();
        hunk = parent.getOrCreateData(key);
    }

    @Override
    public void save() {
        parent.setData(key, hunk);
        parent.save();
    }

    @Override
    public void unload(SurvivalCore plugin) {
        hunk = null;
        key = null;
        parent = null;
    }

    @Override
    public boolean hasData(String key) {
        return hunk.hasKey(applyPrefix(key));
    }

    @Override
    public Optional<Hunk> getData(String key) {
        return hunk.<Map>get(applyPrefix(key)).map(Hunk::new);
    }

    @Override
    public void setData(String key, Hunk data) {
        hunk.set(applyPrefix(key), data.getRaw());
    }

    @Override
    public IStorageProvider setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

}
