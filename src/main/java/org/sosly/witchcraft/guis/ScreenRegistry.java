package org.sosly.witchcraft.guis;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.sosly.witchcraft.guis.screens.PotionPouchScreen;


public class ScreenRegistry {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ContainerRegistry.POTION_POUCH.get(), PotionPouchScreen::new);
    }
}
