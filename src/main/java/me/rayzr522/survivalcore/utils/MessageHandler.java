package me.rayzr522.survivalcore.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Hashtable;

/**
 * @author Rayzr
 */
public class MessageHandler {
    private Hashtable<String, String> messages = new Hashtable<>();

    private static String getBaseKey(String key) {
        return key.substring(0, key.lastIndexOf('.'));
    }

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

    public String trRaw(String key, Object... objects) {
        return ChatColor.translateAlternateColorCodes('&', String.format(messages.getOrDefault(key, key), objects));
    }

    public String tr(String key, Object... objects) {
        return getPrefixFor(key) + trRaw(key, objects);
    }
}
