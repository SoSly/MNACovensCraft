package org.sosly.witchcraft;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue BOSSES_BLOCK_SYMPATHY = BUILDER
            .comment("Should sympathetic magic affect players when they are near bosses?")
            .define("bossesBlockSympathy", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean bossesBlockSympathy;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        bossesBlockSympathy = BOSSES_BLOCK_SYMPATHY.get();
    }
}
