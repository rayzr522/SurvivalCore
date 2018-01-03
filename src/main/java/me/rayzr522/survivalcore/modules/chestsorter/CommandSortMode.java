package me.rayzr522.survivalcore.modules.chestsorter;

import me.rayzr522.survivalcore.api.commands.CommandContext;
import me.rayzr522.survivalcore.api.commands.CommandResult;
import me.rayzr522.survivalcore.api.commands.CommandTarget;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;
import me.rayzr522.survivalcore.utils.Utils;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandSortMode extends ModuleCommand<ChestSorterModule> {
    /**
     * Creates a new {@link ModuleCommand} with the given module.
     *
     * @param module The {@link IModule} to associate this command with.
     */
    protected CommandSortMode(ChestSorterModule module) {
        super(module);
    }

    @Override
    public String getCommandName() {
        return "sortmode";
    }


    @Override
    public String getPermission() {
        return "chestsorter.mode";
    }

    @Override
    public List<CommandTarget> getTargets() {
        return CommandTarget.PLAYER.only();
    }

    @Override
    public CommandResult onCommand(CommandContext ctx) {
        Player player = ctx.getPlayer();

        ChestSortMethod oldMethod = getModule().getMethod(player);
        ChestSortMethod newMethod = ctx.hasArgs()
                ? Utils.getEnumConstant(ChestSortMethod.class, ctx.first()).orElseThrow(ctx.fail("command.sortmode.invalid-mode", ctx.first()))
                : Utils.cycleArray(ChestSortMethod.values(), oldMethod);

        if (oldMethod == newMethod) {
            ctx.tell("command.sortmode.already-using", getName(newMethod));
            return CommandResult.FAIL;
        }

        getModule().setMethod(player, newMethod);

        ctx.tell("command.sortmode.switched", getName(oldMethod), getName(newMethod));

        return CommandResult.SUCCESS;
    }

    private String getName(ChestSortMethod method) {
        return getPlugin().trRaw(String.format("module.chestsorter.mode.%s", method.name().toLowerCase()));
    }
}
