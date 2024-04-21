package org.sosly.witchcraft.events.enchantments;


import com.mna.api.events.RuneforgeEnchantEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.enchantments.EnchantmentRegistry;

@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Dedication {
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
