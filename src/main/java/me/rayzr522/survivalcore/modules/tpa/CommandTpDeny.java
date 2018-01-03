package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpDeny extends ModuleCommand<TpaModule> {
    public CommandTpDeny(TpaModule module) {
        super(module);
    }

    @Override
    public String getCommandName() {
        return "tpdeny";
    }

    @Override
    public String getPermission() {
        return "tpdeny";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("tpno");
    }

    @Override
    public List<CommandTarget> getTargets() {
        return Collections.singletonList(CommandTarget.PLAYER);
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        TpaModule.TpaRequest request = getModule().getCurrentRequest(ctx.getPlayer().getUniqueId())
                .orElseThrow(ctx.fail("command.tpdeny.no-request"));

        getModule().cancelRequest(ctx.getPlayer().getUniqueId());
        request.getRequesterPlayer().ifPresent(player -> ctx.tell(player, "command.tpa.denied"));
        return CommandResult.SUCCESS;
    }
}
