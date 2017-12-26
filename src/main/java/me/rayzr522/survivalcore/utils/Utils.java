package me.rayzr522.survivalcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    /**
     * Fuzzy-finds an online player with the given name. Note: this does not check exact name matches. For exact matching, use {@link #findPlayerExact(String)}.
     *
     * @param name The name of the player to find.
     * @return An {@link Optional} with the player, if any match was found.
     */
    @SuppressWarnings("deprecated")
    public static Optional<Player> findPlayer(String name) {
        List<Player> players = Bukkit.matchPlayer(name);
        return players.size() == 0 ? Optional.empty() : Optional.of(players.get(0));
    }

    /**
     * Finds an online player whose name matches the given name exactly. Note: this checks exact name matches. For fuzzy-finding, use {@link #findPlayer(String)}.
     *
     * @param name The exact name of the player to find.
     * @return An {@link Optional} with the player, if any match was found.
     */
    @SuppressWarnings("deprecated")
    public static Optional<Player> findPlayerExact(String name) {
        return Optional.ofNullable(Bukkit.getPlayer("name"));
    }

    /**
     * Concatenates a list of items with the given joiner string.
     *
     * @param items  The items to concatenate.
     * @param joiner The joiner string.
     * @return The joined string.
     */
    public static String join(List<?> items, String joiner) {
        return items.stream()
                .map(Object::toString)
                .collect(Collectors.joining(joiner));
    }

    /**
     * Concatenates an array of items with the given joiner string.
     *
     * @param items  The items to concatenate.
     * @param joiner The joiner string.
     * @return The joined string.
     */
    public static String join(Object[] items, String joiner) {
        return join(Arrays.asList(items), joiner);
    }

    /**
     * Centers the X and Z coordinates of a {@link Location} so that they are in the center of the block.
     *
     * @param location The location to center.
     * @return The centered location.
     */
    public static Location centerLocation(Location location) {
        return new Location(location.getWorld(), location.getBlockX() + 0.5f, location.getBlockY(), location.getBlockZ() + 0.5f, location.getYaw(), location.getPitch());
    }
}
