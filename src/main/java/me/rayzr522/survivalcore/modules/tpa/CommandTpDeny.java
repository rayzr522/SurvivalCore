package me.rayzr522.survivalcore.modules.tpa;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rayzr522 on 5/27/17.
 */
public class CommandTpDeny extends ManagerCommand<TpaManager> {
    public CommandTpDeny(TpaManager manager) {
        super(manager);
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
        return Arrays.asList(CommandTarget.PLAYER);
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        TpaManager.TpaRequest request = getManager().getCurrentRequest(ctx.getPlayer().getUniqueId())
                .orElseThrow(ctx.fail("command.tpdeny.no-request"));

        getManager().cancelRequest(ctx.getPlayer().getUniqueId());
        request.getRequesterPlayer().ifPresent(player -> ctx.tell(player, "command.tpa.denied"));
        return CommandResult.SUCCESS;
    }
}
