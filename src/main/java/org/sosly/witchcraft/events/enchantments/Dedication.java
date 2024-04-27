package org.sosly.witchcraft.events.enchantments;


import com.mna.api.events.RuneforgeEnchantEvent;
import com.mna.effects.EffectInit;
import com.mna.items.sorcery.ItemStaff;
import com.mysticalchemy.crucible.CrucibleTile;
import com.mysticalchemy.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.enchantments.EnchantmentRegistry;
import org.sosly.witchcraft.enchantments.staves.DedicationEnchantment;

import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Dedication {
    @SubscribeEvent
    public static void onClick(PlayerInteractEvent event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            return;
        }

        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) {
            return;
        }

        if (!(stack.getItem() instanceof ItemStaff staff)) {
            return;
        }

        if (EnchantmentHelper.getEnchantments(stack).get(EnchantmentRegistry.DEDICATION.get()) == null) {
            return;
        }

        BlockPos pos = event.getPos();
        if (pos == null) {
            return;
        }

        BlockEntity tile = level.getBlockEntity(pos);
        if (!(tile instanceof CrucibleTile crucible)) {
            return;
        }

        BlockState state = level.getBlockState(pos);
        AtomicBoolean success = new AtomicBoolean(false);
        crucible.getProminentEffects().forEach((effect, strength) -> {
            if (effect.equals(EffectInit.INSTANT_MANA.get()) || effect.equals(EffectInit.MANA_REGEN.get())) {
                DedicationEnchantment.addMana(stack, (int) (strength * 1.0F));
                success.set(true);
            }
        });

        if (success.get()) {
            int existingLevel = state.getValue(LayeredCauldronBlock.LEVEL);
            if (existingLevel == 1) {
                level.setBlock(pos, BlockInit.EMPTY_CRUCIBLE.get().defaultBlockState(), 3);
            } else {
                level.setBlock(pos, state.setValue(LayeredCauldronBlock.LEVEL, existingLevel - 1), 3);
            }
        }
    }

    @SubscribeEvent
    public static void onRuneforgedEnchantment(RuneforgeEnchantEvent event) {
        ItemStack staff = event.getOutput();
        EnchantmentHelper.getEnchantments(staff).forEach((enchantment, level) -> {
            if (enchantment.equals(EnchantmentRegistry.DEDICATION.get())) {
                EnchantmentRegistry.DEDICATION.get().applyEnchantment(staff, event.getCrafter(), level);
            }
        });
    }

    @SubscribeEvent
    public static void onGrindstone(GrindstoneEvent.OnPlaceItem event) {
        ItemStack top = event.getTopItem();
        ItemStack bottom = event.getBottomItem();
        ItemStack output = event.getOutput();

        if (output.isEmpty()) {
            if (top.isEmpty() ^ bottom.isEmpty()) {
                output = (top.isEmpty() ? bottom : top).copy();
            }
        }

        if (!output.isEmpty()) {
            ItemStack staff = output;
            EnchantmentHelper.getEnchantments(output).forEach((enchantment, level) -> {
                if (enchantment.equals(EnchantmentRegistry.DEDICATION.get())) {
                    EnchantmentRegistry.DEDICATION.get().removeEnchantment(staff);
                    EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(ItemStack.EMPTY), staff);
                    event.setOutput(staff);
                }
            });
        }
    }
}
