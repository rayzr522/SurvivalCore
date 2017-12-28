package me.rayzr522.survivalcore.modules.tpa;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

enum TpaDirection {
    TO_REQUSTER(Entity::teleport, "to", "here"),
    TO_TARGET((target, requester) -> requester.teleport(target), "here", "to");

    private final BiConsumer<Player, Player> handler;
    private final String requesterMessageKey;
    private final String targetMessageKey;

    TpaDirection(BiConsumer<Player, Player> handler, String targetMessageKey, String requesterMessageKey) {
        this.handler = handler;
        this.targetMessageKey = targetMessageKey;
        this.requesterMessageKey = requesterMessageKey;
    }

    void apply(Player target, Player requester) {
        handler.accept(target, requester);
    }

    String getTargetMessageKey() {
        return targetMessageKey;
    }

    String getRequesterMessageKey() {
        return requesterMessageKey;
    }
}
