package org.sosly.witchcraft;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue BOSSES_BLOCK_SYMPATHY = BUILDER
            .comment("Should boss arenas prevent sympathetic magic from working?")
            .define("bossesBlockSympathy", true);

    private static final ForgeConfigSpec.BooleanValue BOSSES_IMMUNE_TO_SYMPATHY = BUILDER
            .comment("Should bosses be immune to sympathetic magic?")
            .define("bossesImmuneToSympathy", true);

    private static final ForgeConfigSpec.IntValue EFFECT_FOR_TIER = BUILDER
            .comment("How many spell effects should a player have to cast on a Witch mob to progress to the next tier?")
            .defineInRange("effectForTier", 3, 1, 10);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean bossesBlockSympathy;
    public static boolean bossesImmuneToSympathy;
    public static int effectForTier;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        bossesBlockSympathy = BOSSES_BLOCK_SYMPATHY.get();
        bossesImmuneToSympathy = BOSSES_IMMUNE_TO_SYMPATHY.get();
        effectForTier = EFFECT_FOR_TIER.get();
    }
}
