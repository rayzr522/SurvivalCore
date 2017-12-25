package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.SurvivalCore;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CommandRegister {
    private SurvivalCore plugin;
    private List<Command> registeredCommands = new LinkedList<>();

    private SimpleCommandMap commandMap;
    private Constructor<PluginCommand> pluginCommandConstructor;
    private Field knownCommandsField;

    public CommandRegister(SurvivalCore plugin) {
        this.plugin = plugin;
    }

    public void init() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        Field commandMapField = plugin.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);
        commandMap = (SimpleCommandMap) commandMapField.get(plugin.getServer());

        pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        pluginCommandConstructor.setAccessible(true);

        knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
        knownCommandsField.setAccessible(true);
    }

    public void unregisterAll() {
        Map<String, Command> internalCommandMap = getCommandMap();

        registeredCommands.forEach(command -> {
            command.unregister(commandMap);

            if (internalCommandMap == null) {
                return;
            }

            internalCommandMap.entrySet().removeIf(entry -> entry.getValue().equals(command));
        });
    }

    private PluginCommand createCommand(String label) {
        try {
            return pluginCommandConstructor.newInstance(label, plugin);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            // This should never happen, so yeah... let's just return null, because that's safe :P
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Command> getCommandMap() {
        if (commandMap == null) {
            return null;
        }

        try {
            return (Map<String, Command>) knownCommandsField.get(commandMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void register(ICommandHandler commandHandler) {
        PluginCommand command = createCommand(commandHandler.getCommandName());

        command.setExecutor(new InternalCommandExecutor(commandHandler));
        command.setAliases(commandHandler.getAliases());

        if (commandMap.register(commandHandler.getCommandName(), plugin.getName(), command)) {
            registeredCommands.add(command);
        }
    }
}
