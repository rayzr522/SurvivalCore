package me.rayzr522.survivalcore.api.managers;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;

import java.util.Collections;
import java.util.List;

/**
 * Represents a manager, which handles data or other various shared aspects of multiple commands or systems.
 * <p>
 * Created by Rayzr522 on 5/27/17.
 */
public interface IManager {
    /**
     * Called when {@link SurvivalCore} loads.
     *
     * @param core The {@link SurvivalCore} instance.
     */
    void onLoad(SurvivalCore core);

    /**
     * @return The name of this manager.
     */
    String getName();

    /**
     * @return Any commands associated with this manager.
     */
    default List<ManagerCommand> getCommands() {
        return Collections.emptyList();
    }
}
