package org.sosly.witchcraft.items.alchemy;

import com.mna.api.items.ITieredItem;
import com.mna.items.base.IRadialInventorySelect;
import com.mna.items.base.ItemBagBase;
import com.mna.items.filters.ItemFilterGroup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.sosly.witchcraft.guis.providers.PotionPouchProvider;
import org.sosly.witchcraft.inventories.PotionPouchInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PotionPouchItem extends ItemBagBase implements IRadialInventorySelect, ITieredItem<PotionPouchItem> {
    private int tier;

    public PotionPouchItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public int getCachedTier() {
        return this.tier;
    }

    @Override
    public void setCachedTier(int tier) {
        this.tier = tier;
    }

    public int capacity(ItemStack stack) {
        return getInventory(stack).getSlots();
    }

    @Override
    public int capacity() {
        return 5;
    }

    @Override
    public int capacity(@Nullable Player player) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() instanceof PotionPouchItem) {
            return ((PotionPouchItem) stack.getItem()).capacity(stack);
        }
        return 0;
    }

    @Override
    public ItemFilterGroup filterGroup() {
        return null;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        ItemStack potionStack = getPotionStack(stack);
        if (potionStack.isEmpty()) {
            return stack;
        }
        potionStack.finishUsingItem(level, entity);
        getInventory(stack).setStackInSlot(getIndex(stack), potionStack);
        return stack;
    }

    @Override
    public IItemHandlerModifiable getInventory(ItemStack itemStack) {
        return this.getInventory(itemStack, null);
    }

    @Override
    public IItemHandlerModifiable getInventory(ItemStack itemStack, @Nullable Player player) {
        return new PotionPouchInventory(itemStack);
    }

    @Override
    public int getIndex(ItemStack stack) {
        return stack.getOrCreateTag().getInt("radial_index");
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        ItemStack potionStack = getPotionStack(stack);
        if (potionStack.isEmpty()) {
            return 0;
        }
        return potionStack.getItem().getUseDuration(potionStack);
    }

    @Override
    public void setIndex(@Nullable Player player, InteractionHand hand, ItemStack stack, int idx) {
        this.setIndex(stack, idx);
    }

    @Override
    public void setIndex(ItemStack stack, int idx) {
        stack.getOrCreateTag().putInt("radial_index", idx);
    }

    public ItemStack getPotionStack(ItemStack stack) {
        return getInventory(stack).getStackInSlot(getIndex(stack));
    }

    @Override
    public MenuProvider getProvider(ItemStack itemStack) {
        return new PotionPouchProvider(itemStack);
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand hand) {
        boolean guiOpened = false;
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND)  {
            guiOpened = this.openGuiIfModifierPressed(player.getItemInHand(hand), player, level);
        }

        if (guiOpened) {
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        if (level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        // Drink the potion!
        ItemStack pouch = player.getItemInHand(hand);
        ItemStack potionStack = getPotionStack(pouch);
        if (potionStack.isEmpty()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        potionStack.use(player.getCommandSenderWorld(), player, hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.PASS;
        }
        return this.use(context.getLevel(), player, context.getHand()).getResult();
    }
}
