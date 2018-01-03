package me.rayzr522.survivalcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Hashtable;

/**
 * @author Rayzr
 */
public class MessageHandler {
    private final Hashtable<String, String> messages = new Hashtable<>();

    private static String getBaseKey(String key) {
        int index = key.lastIndexOf('.');
        if (index < 0) {
            return "";
        }
        return key.substring(0, index);
    }

    /**
     * Loads all messages from a config.
     *
     * @param config The config to load messages from.
     */
    public void load(ConfigurationSection config) {
        messages.clear();
        config.getKeys(true).forEach(key -> messages.put(key, config.get(key).toString()));
    }

    private String getPrefixFor(String key) {
        String baseKey = getBaseKey(key);
        String basePrefix = messages.getOrDefault(baseKey + ".prefix", messages.getOrDefault("prefix", ""));
        String addon = messages.getOrDefault(baseKey + ".prefix-addon", "");

        return ChatColor.translateAlternateColorCodes('&', basePrefix + addon);
    }

    /**
     * Translates the message with the given key without the prefix, using the provided objects for translating.
     *
     * @param key     The key of the message.
     * @param objects The objects to translate with.
     * @return The translated message.
     */
    public String trRaw(String key, Object... objects) {
        return ChatColor.translateAlternateColorCodes('&', String.format(messages.getOrDefault(key, key), objects));
    }

    /**
     * Translates the message with the given key, prepending the prefix, and using the provided objects for translating.
     *
     * @param key     The key of the message.
     * @param objects The objects to translate with.
     * @return The translated message with a prefix.
     */
    public String tr(String key, Object... objects) {
        return getPrefixFor(key) + trRaw(key, objects);
    }
}
