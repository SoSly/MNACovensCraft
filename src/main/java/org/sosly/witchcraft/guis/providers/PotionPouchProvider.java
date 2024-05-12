package org.sosly.witchcraft.guis.providers;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sosly.witchcraft.guis.containers.PotionPouchContainer;
import org.sosly.witchcraft.inventories.PotionPouchInventory;

public class PotionPouchProvider implements MenuProvider {
    private final ItemStack pouch;

    public PotionPouchProvider(ItemStack pouch) {
        this.pouch = pouch;
    }

    @Nullable
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new PotionPouchContainer(i, playerInventory, new PotionPouchInventory(this.pouch));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("item.mnaw.potion_pouch");
    }
}
