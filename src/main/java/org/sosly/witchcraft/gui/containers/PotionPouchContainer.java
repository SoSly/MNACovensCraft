package org.sosly.witchcraft.gui.containers;

import com.mna.gui.containers.slots.ExtendedItemStackSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.sosly.witchcraft.gui.ContainerRegistry;
import org.sosly.witchcraft.gui.slots.PotionSlot;
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
