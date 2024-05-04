package org.sosly.witchcraft.guis.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class PotionSlot extends SlotItemHandler {
    public PotionSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        if (stack.getItem() instanceof PotionItem) {
            return 16;
        } else {
            return super.getMaxStackSize(stack);
        }
    }
}
