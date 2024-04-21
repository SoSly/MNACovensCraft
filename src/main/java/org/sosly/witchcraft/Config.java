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

    private static final ForgeConfigSpec.IntValue DEDICATION_CHARGES = BUILDER
            .comment("How many charges should Dedication I give to a staff with 1 mana cost? (divide by mana cost to get charges for other spells)")
            .defineInRange("dedicationCharges", 5000, 10, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue DEDICATION_TIER_MULTIPLIER = BUILDER
            .comment("How much should the charge modifier increase per tier? (multiplier = 1 + (tier - 1) * this value)")
            .defineInRange("dedicationChargeModifier", 0.25, 0.01, 100);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean bossesBlockSympathy;
    public static boolean bossesImmuneToSympathy;
    public static int dedicationCharges;
    public static double dedicationTierMultiplier;
    public static int effectForTier;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        bossesBlockSympathy = BOSSES_BLOCK_SYMPATHY.get();
        bossesImmuneToSympathy = BOSSES_IMMUNE_TO_SYMPATHY.get();
        dedicationCharges = DEDICATION_CHARGES.get();
        dedicationTierMultiplier = DEDICATION_TIER_MULTIPLIER.get();
        effectForTier = EFFECT_FOR_TIER.get();
    }
}
