package org.sosly.witchcraft.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.enchantments.staves.Dedication;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Witchcraft.MOD_ID);
    public static final RegistryObject<Dedication> DEDICATION = ENCHANTMENTS.register("dedication", Dedication::new);
}
