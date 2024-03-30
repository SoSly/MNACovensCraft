package org.sosly.witchcraft.factions;

import com.mna.Registries;
import com.mna.api.faction.IFaction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import org.sosly.witchcraft.Witchcraft;

@Mod.EventBusSubscriber(modid = Witchcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FactionRegistry {
    public static final IFaction COVEN = new Coven();

    @SubscribeEvent
    public static void registerFactions(RegisterEvent event) {
        event.register(((IForgeRegistry) Registries.Factions.get()).getRegistryKey(), (helper) -> {
            helper.register(new ResourceLocation(Witchcraft.MODID,"coven"), COVEN);
        });
    }
}
