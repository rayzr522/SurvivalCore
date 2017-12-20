package me.rayzr522.survivalcore;

import me.rayzr522.survivalcore.api.commands.ICommandHandler;
import me.rayzr522.survivalcore.api.managers.IManager;
import me.rayzr522.survivalcore.modules.tpa.TpaManager;
import me.rayzr522.survivalcore.utils.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * @author Rayzr
 */
public class SurvivalCore extends JavaPlugin {
    private static SurvivalCore instance;

    private MessageHandler messages = new MessageHandler();
    private List<IManager> managers = new ArrayList<>();

    public static SurvivalCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        registerManagers();
        registerCommands();

        managers.forEach(manager -> {
            manager.onLoad(this);
            manager.getCommands().forEach(this::registerCommand);
        });

        reload();
    }

    private void registerManagers() {
        registerManager(new TpaManager());
    }

    private void registerCommands() {
        // Currently unused
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * @param manager The {@link IManager manager} to register
     */
    public void registerManager(IManager manager) {
        managers.add(manager);
    }

    /**
     * @param handler The {@link ICommandHandler command handler} to register
     */
    public void registerCommand(ICommandHandler handler) {
        Objects.requireNonNull(handler, "handler cannot be null!");

        PluginCommand command = getCommand(handler.getCommandName());
        if (command == null) {
            throw new IllegalArgumentException("Could not find command '" + handler.getCommandName() + "'");
        }

        command.setExecutor(handler);
    }

    public IManager getManager(Class<IManager> managerClass) {
        return managers.stream().filter(manager -> managerClass == manager.getClass()).findFirst().orElse(null);
    }

    public IManager getManager(String name) {
        return managers.stream().filter(manager -> manager.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * (Re)loads all configs from the disk
     */
    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        messages.load(getConfig("messages.yml"));
    }

    /**
     * If the file is not found and there is a default file in the JAR, it saves the default file to the plugin data folder first
     *
     * @param path The path to the config file (relative to the plugin data folder)
     * @return The {@link YamlConfiguration}
     */
    public YamlConfiguration getConfig(String path) {
        if (!getFile(path).exists() && getResource(path) != null) {
            saveResource(path, true);
        }
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    /**
     * Attempts to save a {@link YamlConfiguration} to the disk, and any {@link IOException}s are printed to the console
     *
     * @param config The config to save
     * @param path   The path to save the config file to (relative to the plugin data folder)
     */
    public void saveConfig(YamlConfiguration config, String path) {
        try {
            config.save(getFile(path));
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save config", e);
        }
    }

    /**
     * @param path The path of the file (relative to the plugin data folder)
     * @return The {@link File}
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.separatorChar));
    }

    /**
     * Returns a message from the language file
     *
     * @param key     The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String tr(String key, Object... objects) {
        return messages.tr(key, objects);
    }

    /**
     * Returns a message from the language file without adding the prefix
     *
     * @param key     The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String trRaw(String key, Object... objects) {
        return messages.trRaw(key, objects);
    }

    /**
     * Checks a target {@link CommandSender} for a given permission (excluding the permission base). Example:
     * <p>
     * <pre>
     *     checkPermission(sender, "command.use", true);
     * </pre>
     * <p>
     * This would check if the player had the permission <code>"{plugin name}.command.use"</code>, and if they didn't, it would send them the no-permission message from the messages config file.
     *
     * @param target      The target {@link CommandSender} to check
     * @param permission  The permission to check, excluding the permission base (which is the plugin name)
     * @param sendMessage Whether or not to send a no-permission message to the target
     * @return Whether or not the target has the given permission
     */
    public boolean checkPermission(CommandSender target, String permission, boolean sendMessage) {
        String fullPermission = String.format("%s.%s", getName(), permission);

        if (!target.hasPermission(fullPermission)) {
            if (sendMessage) {
                target.sendMessage(tr("no-permission", fullPermission));
            }

            return false;
        }

        return true;
    }

    /**
     * @return The {@link MessageHandler} instance for this plugin
     */
    public MessageHandler getMessages() {
        return messages;
    }

}
