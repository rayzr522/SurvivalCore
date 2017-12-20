package me.rayzr522.survivalcore.api.commands;

import me.rayzr522.survivalcore.api.managers.IManager;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public abstract class ManagerCommand<T extends IManager> implements ICommandHandler {
    private final T manager;

    public ManagerCommand(T manager) {
        this.manager = manager;
    }

    /**
     * @return The {@link IManager manager} that this command is associated with
     */
    public T getManager() {
        return manager;
    }
}
