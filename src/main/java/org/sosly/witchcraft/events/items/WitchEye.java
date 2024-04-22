package org.sosly.witchcraft.events.items;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.items.alchemy.WitchEyeItem;

@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WitchEye {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        Player player = event.getEntity();
        if (player == null) {
            return;
        }

        if (!WitchEyeItem.hasWitchEye(player)) {
            return;
        }

        WitchEyeItem.revealAlchemicalProperties(player.level(), event.getItemStack(), event.getToolTip());
    }
}
