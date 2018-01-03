package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.api.modules.IModule;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public abstract class ModuleCommand<T extends IModule> implements ICommandHandler {
    private final T module;

    /**
     * Creates a new {@link ModuleCommand} with the given module.
     *
     * @param module The {@link IModule} to associate this command with.
     */
    protected ModuleCommand(T module) {
        this.module = module;
    }

    /**
     * @return The {@link IModule module} that this command is associated with.
     */
    protected T getModule() {
        return module;
    }
}
