package me.rayzr522.survivalcore.modules.pvptoggle;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;
import me.rayzr522.survivalcore.api.modules.AbstractModule;
import me.rayzr522.survivalcore.api.storage.impl.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collections;
import java.util.List;

public class PVPToggleModule extends AbstractModule implements Listener {
    private CommandPVPToggle commandPvpToggle;

    @Override
    public void onLoad(SurvivalCore core) {
        super.onLoad(core);

        commandPvpToggle = new CommandPVPToggle(this);

        // TODO: We need to be able to then unregister this in the future... hmm.
        core.getServer().getPluginManager().registerEvents(this, core);
    }

    @Override
    public String getName() {
        return "PVPToggle";
    }

    @Override
    public List<ModuleCommand> getCommands() {
        return Collections.singletonList(commandPvpToggle);
    }

    private boolean isEnabled(Player player) {
        return getPlayerData(player.getUniqueId()).getBoolean("enabled").orElse(true);
    }

    public boolean togglePVP(Player player) {
        PlayerData data = getPlayerData(player.getUniqueId());
        boolean currentState = !isEnabled(player);

        data.set("enabled", currentState);
        data.save();

        return currentState;
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();

            if (getPlugin().checkPermission(player, "pvptoggle.use", false) && !isEnabled(player) || !isEnabled(damager)) {
                e.setCancelled(true);
            }
        }
    }
}
