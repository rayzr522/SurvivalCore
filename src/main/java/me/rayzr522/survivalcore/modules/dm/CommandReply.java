package me.rayzr522.survivalcore.modules.dm;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ManagerCommand;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandReply extends ManagerCommand<DMManager> {
    public CommandReply(DMManager manager) {
        super(manager);
    }

    @Override
    public String getCommandName() {
        return "reply";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("r");
    }

    @Override
    public String getPermission() {
        return "dm.reply";
    }

    @Override
    public List<CommandTarget> getTargets() {
        return CommandTarget.PLAYER.only();
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        if (!ctx.hasArgs()) {
            return CommandResult.SHOW_USAGE;
        }

        Player target = getManager().getLastTarget(ctx.getPlayer())
                .orElseThrow(ctx.fail("command.reply.no-conversation"));

        getManager().sendDM(ctx.getPlayer(), target, ctx.remainder());

        return CommandResult.SUCCESS;
    }
}
