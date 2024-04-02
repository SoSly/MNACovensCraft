package org.sosly.witchcraft.items.sympathy;

import com.mna.api.items.TieredItem;
import com.mna.items.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.sosly.witchcraft.items.ItemRegistry;

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
