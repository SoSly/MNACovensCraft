package org.sosly.witchcraft.enchantments.artifacts;

import com.mna.enchantments.base.MAEnchantmentBase;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.sosly.witchcraft.items.ItemRegistry;

public class PotionImmune extends MAEnchantmentBase {
    public PotionImmune() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{});
    }

    public boolean canEnchant(ItemStack stack) {
        if (!stack.getItem().equals(ItemRegistry.POTION_AMULET.get())) {
            return false;
        }

        if (stack.getOrCreateTag().contains("potion_immune")) {
            return false;
        }

        return true;
    }
}
