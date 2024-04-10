package org.sosly.witchcraft.items.sympathy;

import com.mna.api.items.TieredItem;
import com.mna.items.ItemInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BloodyNeedleItem extends TieredItem {
    public BloodyNeedleItem() {
        super((new Item.Properties()).stacksTo(1));
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack item) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack item) {
        return new ItemStack(ItemInit.VINTEUM_NEEDLE.get());
    }
}
