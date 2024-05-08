package org.sosly.witchcraft.inventories;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.sosly.witchcraft.items.alchemy.PotionPouchItem;

public class PotionPouchInventory extends ItemStackHandler {
    protected final ItemStack pouch;
    protected int potionSlots = 5;

    public PotionPouchInventory(ItemStack item) {
        super(5);
        this.pouch = item;
        readItemStack();
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot < potionSlots) {
            return PotionPouchItem.getPotionDepth(pouch);
        }
        return 64;
    }

    @Override
    public int getSlots() {
        return potionSlots;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (slot < potionSlots) {
            super.setStackInSlot(slot, stack);
            writeItemStack();
        }
    }

    @Override
    public int getStackLimit(int slot, @NotNull ItemStack stack) {
        if (slot < potionSlots) {
            return PotionPouchItem.getPotionDepth(pouch);
        }
        return stack.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack itemStack) {
        if (slot < potionSlots) {
            return PotionUtils.getPotion(itemStack) != Potions.EMPTY;
        }
        return false;
    }

    public void readItemStack() {
        if (pouch.getTag() != null && pouch.getTag().contains("contents")) {
            this.deserializeNBT(pouch.getTag().getCompound("contents"));
        }
    }

    public void writeItemStack() {
        pouch.getOrCreateTag().put("contents", serializeNBT());
    }
}
