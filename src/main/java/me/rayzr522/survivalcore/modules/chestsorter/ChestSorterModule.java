package me.rayzr522.survivalcore.modules.chestsorter;

import me.rayzr522.survivalcore.SurvivalCore;
import me.rayzr522.survivalcore.api.commands.ModuleCommand;
import me.rayzr522.survivalcore.api.modules.AbstractModule;
import me.rayzr522.survivalcore.api.storage.impl.PlayerData;
import me.rayzr522.survivalcore.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestSorterModule extends AbstractModule implements Listener {
    private CommandSortMode commandSortMode;

    @Override
    public void onLoad(SurvivalCore core) {
        super.onLoad(core);

        commandSortMode = new CommandSortMode(this);

        core.getServer().getPluginManager().registerEvents(this, core);
    }

    @Override
    public String getName() {
        return "ChestSorter";
    }

    @Override
    public List<ModuleCommand> getCommands() {
        return Collections.singletonList(commandSortMode);
    }

    public ChestSortMethod getMethod(Player player) {
        return getPlayerData(player.getUniqueId()).getEnumConstant("method", ChestSortMethod.class).orElse(ChestSortMethod.ID);
    }

    public void setMethod(Player player, ChestSortMethod chestSortMethod) {
        PlayerData playerData = getPlayerData(player.getUniqueId());
        playerData.set("method", chestSortMethod.name());
        playerData.save();
    }

    private long countItems(ItemStack[] items) {
        return Arrays.stream(items).map(Utils::optionalItem).filter(Optional::isPresent).count();
    }

    private void flattenItems(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        inventory.clear();
        Arrays.stream(contents).filter(Objects::nonNull).forEach(inventory::addItem);
    }

    public void sort(Player player, Block block) {
        Objects.requireNonNull(player, "player cannot be null!");
        Objects.requireNonNull(block, "block cannot be null!");

        if (!player.isSneaking() || !getPlugin().checkPermission(player, "chestsorter.use", false)) {
            return;
        }

        if (!(block.getState() instanceof Chest || block.getState() instanceof DoubleChest)) {
            return;
        }

        InventoryHolder chest = (InventoryHolder) block.getState();
        Inventory inventory = chest.getInventory();
        flattenItems(inventory);
        ItemStack[] contents = inventory.getContents();

        Arrays.sort(contents, getMethod(player));

        inventory.clear();
        inventory.setContents(contents);

        player.sendMessage(getPlugin().tr("module.chestsorter.sorted"));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        // You can never be too careful with Bukkit O_o
        if (e.getClickedBlock() == null || e.getPlayer() == null) {
            return;
        }

        sort(e.getPlayer(), e.getClickedBlock());
    }
}
