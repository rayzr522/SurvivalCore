package me.rayzr522.survivalcore.api.managers;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public interface IManager {
    void onLoad(SurvivalCore core);

    String getName();

    default List<ManagerCommand> getCommands() {
        return Collections.emptyList();
    }
}
