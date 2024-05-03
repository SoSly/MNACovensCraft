package org.sosly.witchcraft.items.alchemy;

import com.mna.api.items.ITieredItem;
import com.mna.items.base.IRadialMenuItem;
import com.mna.items.base.ItemBagBase;
import com.mna.items.filters.ItemFilterGroup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.sosly.witchcraft.gui.providers.PotionPouchProvider;

import javax.annotation.Nonnull;

public class PotionPouchItem extends ItemBagBase implements IRadialMenuItem, ITieredItem<PotionPouchItem> {
    private int tier;

    public PotionPouchItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void setCachedTier(int tier) {
        this.tier = tier;
    }

    @Override
    public int getCachedTier() {
        return this.tier;
    }

    @Override
    public ItemFilterGroup filterGroup() {
        return null;
    }

    @Override
    public MenuProvider getProvider(ItemStack itemStack) {
        return new PotionPouchProvider(itemStack);
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND)  {
            this.openGuiIfModifierPressed(player.getItemInHand(hand), player, level);
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }
}
