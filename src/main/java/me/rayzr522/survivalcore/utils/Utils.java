package me.rayzr522.survivalcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Utils {
    public static Optional<Player> findPlayer(String name) {
        List<Player> players = Bukkit.matchPlayer(name);
        return players.size() == 0 ? Optional.empty() : Optional.of(players.get(0));
    }

    public static String join(List<?> items, String joiner) {
        return items.stream()
                .map(Object::toString)
                .collect(Collectors.joining(joiner));
    }

    public static String join(Object[] items, String joiner) {
        return join(Arrays.asList(items), joiner);
    }

    public static Location centerLocation(Location location) {
        return new Location(location.getWorld(), location.getBlockX() + 0.5f, location.getBlockY(), location.getBlockZ() + 0.5f, location.getYaw(), location.getPitch());
    }
}
