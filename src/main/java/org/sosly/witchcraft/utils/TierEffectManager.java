package org.sosly.witchcraft.utils;

import com.mna.api.spells.parts.SpellEffect;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TierEffectManager is a utility class for the CovenCapability.
 * This class manages the SpellEffects that a coven witch must cast on a Witch mob
 * in order to progress to the next tier of magic.
 */
public class TierEffectManager {
    private final Map<Integer, Set<SpellEffect>> tierSpells = new HashMap<>();
    private final RandomSource rand;

    public TierEffectManager(RandomSource rand) {
        this.rand = rand;
    }
}
