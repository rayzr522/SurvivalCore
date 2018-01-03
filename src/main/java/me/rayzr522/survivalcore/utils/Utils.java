package me.rayzr522.survivalcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
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
     * Gets the online player with the given UUID.
     *
     * @param uuid The UUID of the player to get.
     * @return An {@link Optional} with the player, if that player is online.
     */
    public static Optional<Player> getPlayer(UUID uuid) {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }

    /**
     * Returns an {@link Optional} based on whether or not the item is null <i>as well as whether or not the item has a type of {@link Material#AIR}</i>.
     *
     * @param item The item to check.
     * @return An {@link Optional} containing the item.
     */
    public static Optional<ItemStack> optionalItem(ItemStack item) {
        return (item == null || item.getType() == Material.AIR) ? Optional.empty() : Optional.of(item);
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

    /**
     * Gets an enum constant of the given type with the given name.
     *
     * @param enumType The enum class of the constant.
     * @param name     The name of the constant.
     * @param <T>      The type of the enum class.
     * @return An {@link Optional} containing the enum constant.
     */
    public static <T extends Enum<T>> Optional<T> getEnumConstant(Class<T> enumType, String name) {
        Objects.requireNonNull(enumType, "enumType cannot be null!");
        Objects.requireNonNull(name, "name cannot be null!");

        try {
            return Optional.of(Enum.valueOf(enumType, name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private static <T> int indexOf(T[] items, T item) {
        Objects.requireNonNull(items, "items cannot be null!");

        for (int i = 0; i < items.length; i++) {
            if (Objects.equals(items[i], item)) {
                return i;
            }
        }

        return -1;
    }

    private static <T> T cycleArray(T[] items, int currentIndex) {
        return items[++currentIndex % items.length];
    }

    /**
     * Gets the next item in an array, wrapping around when the end is reached.
     *
     * @param items   The array.
     * @param current The current item in the array.
     * @param <T>     The type of the array.
     * @return The next item.
     */
    public static <T> T cycleArray(T[] items, T current) {
        return cycleArray(items, indexOf(items, current));
    }
}
