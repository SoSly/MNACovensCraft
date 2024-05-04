package org.sosly.witchcraft.guis.containers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.sosly.witchcraft.guis.ContainerRegistry;
import org.sosly.witchcraft.guis.slots.PotionSlot;
import org.sosly.witchcraft.inventories.PotionPouchInventory;

public class PotionPouchContainer extends AbstractPlayerInventoryContainer {
    private PotionPouchInventory inventory;
    private final boolean isClientside;

    public PotionPouchContainer(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(i, playerInventory, new PotionPouchInventory(playerInventory.getItem(playerInventory.selected)));
    }

    public PotionPouchContainer(int windowId, Inventory playerInventory, PotionPouchInventory potionPouchInventory) {
        super(ContainerRegistry.POTION_POUCH.get(), windowId);
        this.isClientside = playerInventory.player.level().isClientSide();
        this.inventory = potionPouchInventory;
        this.initializeSlots(playerInventory);
    }

    @Override
    public void broadcastChanges() {
        if (!this.isClientside) {
            this.inventory.writeItemStack();
        }

        super.broadcastChanges();
    }

    @Override
    public int getSlotCount() {
        return this.inventory.getSlots();
    }

    public int getSlotSize(int idx, ItemStack stack) {
        return this.inventory.getStackLimit(idx, stack);
    }

    protected void initializeSlots(Inventory playerInventory) {
        int slotIndex = 0;

        int row = 0;
        int col;
        for (col = 0; col < 5; ++col) {
            int slotX = 40 + col * 18;
            int slotY = 102 + row * 18;
            this.addSlot(new PotionSlot(this.inventory, slotIndex++, slotX, slotY));
        }

        super.initializeSlots(playerInventory, slotIndex);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
