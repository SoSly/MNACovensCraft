package org.sosly.witchcraft.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.api.capabilities.ICovenCapability;
import org.sosly.witchcraft.capabilities.coven.CovenProvider;

@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Factions {
    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<?> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(ICovenCapability.COVEN_CAPABILITY, new CovenProvider());
        }
    }
}
