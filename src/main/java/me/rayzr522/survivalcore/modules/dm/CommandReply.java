package me.rayzr522.survivalcore.modules.dm;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandReply extends ModuleCommand<DMModule> {
    public CommandReply(DMModule module) {
        super(module);
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

        Player target = getModule().getLastTarget(ctx.getPlayer())
                .orElseThrow(ctx.fail("command.reply.no-conversation"));

        getModule().sendDM(ctx.getPlayer(), target, ctx.remainder());

        return CommandResult.SUCCESS;
    }
}
