package org.sosly.witchcraft.gui.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

abstract class AbstractPlayerInventoryContainer extends AbstractContainerMenu {
    protected AbstractPlayerInventoryContainer(@Nullable MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    void initializeSlots(Inventory playerInventory, int slotIndex) {
        int col;
        for (int row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                int slot = col + row * 9 + 9;
                int slotX = 48 + col * 18;
                int slotY = 129 + row * 18;

                this.addSlot(new Slot(playerInventory, slot, slotX, slotY));

                ++slotIndex;
            }
        }

        for(col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 48 + col * 18, 187));
            ++slotIndex;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        int playerSlots = 36;
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            if (pIndex < playerSlots) {
                if (!this.moveItemStackTo(slotStack, playerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, playerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
}
