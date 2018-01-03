package me.rayzr522.survivalcore.modules.pvptoggle;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;

import java.util.Collections;
import java.util.List;

public class CommandPVPToggle extends ModuleCommand<PVPToggleModule> {
    public CommandPVPToggle(PVPToggleModule module) {
        super(module);
    }

    @Override
    public String getCommandName() {
        return "pvptoggle";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("pvp");
    }

    @Override
    public String getPermission() {
        return "pvptoggle";
    }

    @Override
    public List<CommandTarget> getTargets() {
        return CommandTarget.PLAYER.only();
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        boolean enabled = getModule().togglePVP(ctx.getPlayer());

        ctx.tell("command.pvptoggle.toggled", enabled ? getPlugin().tr("constants.enabled") : getPlugin().tr("constants.disabled"));

        return CommandResult.SUCCESS;
    }
}
