package me.rayzr522.survivalcore.api.storage.impl;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.storage.Hunk;
import me.rayzr522.survivalcore.api.storage.IStorageProvider;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class YamlStorageProvider implements IStorageProvider {
    private String prefix = "";

    private final File file;
    private YamlConfiguration internal;

    public YamlStorageProvider(File file) {
        Objects.requireNonNull(file, "file cannot be null!");

        this.file = file;
    }

    @Override
    public void load(SurvivalCore plugin) {
        // TODO: Unused?
    }

    @Override
    public void reload() {
        this.internal = YamlConfiguration.loadConfiguration(file);

    }

    @Override
    public void save() {
        try {
            internal.save(file);
        } catch (IOException e) {
            // HEH GUYS LOOK AT MY PRODUCTION-GRADE ERROR HANDLING :D
            // MEH, THE DATA FILE FAILED TO SAVE? BAH, WHO CARES, JUST THROW 'EM A STACKTRACE ;D
            e.printStackTrace();
        }
    }

    @Override
    public void unload(SurvivalCore plugin) {
        internal = null;
    }

    @Override
    public boolean hasData(String key) {
        return getData(key).isPresent();
    }

    @Override
    public Optional<Hunk> getData(String key) {
        return Optional.ofNullable(internal.getConfigurationSection(applyPrefix(key)))
                .map(this::toMap)
                .map(Hunk::new);
    }

    @Override
    public void setData(String key, Hunk data) {
        internal.createSection(applyPrefix(key), data.getRaw());
    }

    @Override
    public IStorageProvider setPrefix(String prefix) {
        Objects.requireNonNull(prefix, "prefix cannot be null!");
        this.prefix = prefix;
        return this;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public IStorageProvider fork(String prefix) {
        return new HunkStorageProvider(getOrCreateData(applyPrefix(prefix)), prefix, this);
    }

    private Map<String, Object> toMap(ConfigurationSection section) {
        return section.getKeys(false).stream().collect(Collectors.toMap(key -> key, key -> {
            if (section.isConfigurationSection(key)) {
                return toMap(section.getConfigurationSection(key));
            }
            return section.get(key);
        }));
    }
}
