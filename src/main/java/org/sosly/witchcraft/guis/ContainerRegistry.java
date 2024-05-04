package org.sosly.witchcraft.guis;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.guis.containers.PotionPouchContainer;

@Mod.EventBusSubscriber(modid=Witchcraft.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Witchcraft.MOD_ID);
    public static final RegistryObject<MenuType<PotionPouchContainer>> POTION_POUCH = CONTAINERS.register("potion_pouch", () -> {
        return IForgeMenuType.create(PotionPouchContainer::new);
    });
}
