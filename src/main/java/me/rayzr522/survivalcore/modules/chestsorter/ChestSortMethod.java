package me.rayzr522.survivalcore.modules.chestsorter;

import me.rayzr522.survivalcore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.Optional;

public enum ChestSortMethod implements Comparator<ItemStack> {
    ID((a, b) -> {
        int idA = a.map(ItemStack::getTypeId).orElse(Integer.MAX_VALUE);
        int idB = b.map(ItemStack::getTypeId).orElse(Integer.MAX_VALUE);
        int idComparator = Integer.compare(idA, idB);

        if (idComparator == 0) {
            return Short.compare(a.map(ItemStack::getDurability).orElse(Short.MAX_VALUE), b.map(ItemStack::getDurability).orElse(Short.MAX_VALUE));
        }

        return idComparator;
    }),
    NAME((a, b) -> {
        String nameA = a.map(ItemStack::getType).map(Material::toString).orElse("");
        String nameB = b.map(ItemStack::getType).map(Material::toString).orElse("");

        if (nameA.equals(nameB)) {
            return ID.comparator.compare(a, b);
        }

        boolean emptyA = nameA.isEmpty();
        boolean emptyB = nameB.isEmpty();

        if (emptyA || emptyB) {
            return Boolean.compare(emptyA, emptyB);
        }

        return nameA.compareToIgnoreCase(nameB);
    });

    private final Comparator<Optional<ItemStack>> comparator;

    ChestSortMethod(Comparator<Optional<ItemStack>> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(ItemStack a, ItemStack b) {
        return comparator.compare(Utils.optionalItem(a), Utils.optionalItem(b));
    }
}
