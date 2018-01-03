/*
TODO LIST:

- SQLite storage provider
- MySQL storage provider
- Maybe separate the default methods in IStorageProvider into a separate abstract class / interface?
 */

package me.rayzr522.survivalcore.api.storage;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.storage.impl.HunkStorageProvider;

import java.io.IOException;
import java.util.Optional;

public interface IStorageProvider {
    /**
     * Called when the plugin loads. Use this for one-time initialization code. (Note: Use {@link #reload()} for loading data, it is called after this, as well as in any other code that must load the data)
     *
     * @param plugin The {@link SurvivalCore} instance.
     */
    void load(SurvivalCore plugin);

    /**
     * This is called when the plugin starts, as well as in any other code that must reload data from the data-source.
     */
    void reload();

    /**
     * This is called whenever data needs to be persisted.
     */
    void save();

    /**
     * Called when the plugin unloads.
     *
     * @param plugin The {@link SurvivalCore} instance.
     */
    void unload(SurvivalCore plugin);

    /**
     * Checks whether or not there is a hunk at the given key.
     *
     * @param key The key to check.
     * @return Whether or not there is a hunk at the given key.
     */
    boolean hasData(String key);

    /**
     * Gets a hunk with the given key.
     *
     * @param key The key of the hunk.
     * @return An {@link Optional} containing the hunk.
     */
    Optional<Hunk> getData(String key);

    /**
     * Sets a hunk to the given key.
     *
     * @param key  The key to set.
     * @param data The data to set.
     */
    void setData(String key, Hunk data);

    /**
     * Gets a hunk with the given key, creating an empty hunk if none exists at that key.
     *
     * @param key The key of the hunk.
     * @return The hunk at the given key.
     */
    default Hunk getOrCreateData(String key) {
        return getData(key).orElseGet(() -> {
            Hunk newHunk = Hunk.empty();
            setData(key, newHunk);
            return newHunk;
        });
    }

    // TODO: Do we need these following two methods?
    // <T> Optional<T> getValue(String dataKey, String valueKey);
    // void setValue(String dataKey, String valueKey, Object value);

    /**
     * Applies the prefix to the given key, returning the transformed string.
     *
     * @param key The key to apply the prefix to.
     * @return The transformed string.
     */
    default String applyPrefix(String key) {
        if (getPrefix().isEmpty()) {
            return key;
        } else {
            return String.format("%s.%s", getPrefix(), key);
        }
    }

    /**
     * Sets the data prefix for getting data by key.
     *
     * @param prefix The prefix to set.
     * @return This {@link IStorageProvider} instance.
     */
    IStorageProvider setPrefix(String prefix);

    /**
     * Gets the data prefix for getting data by key.
     *
     * @return The prefix.
     */
    String getPrefix();

    /**
     * Forks this {@link IStorageProvider} and creates a new storage provider with the given prefix.
     *
     * @param prefix The new prefix to fork to.
     * @return The forked {@link IStorageProvider}.
     */
    default IStorageProvider fork(String prefix) {
        return new HunkStorageProvider(getOrCreateData(applyPrefix(prefix)), prefix, this);
    }
}
