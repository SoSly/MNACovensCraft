package org.sosly.witchcraft.api.capabilities;

import com.mna.api.spells.parts.SpellEffect;
import net.minecraft.resources.ResourceLocation;
import org.sosly.witchcraft.Witchcraft;

import java.util.Collection;
import java.util.Set;

/**
 * ICovenCapability tracks a coven witch's progression.
 */
public interface ICovenCapability {
    ResourceLocation COVEN_CAPABILITY = new ResourceLocation(Witchcraft.MOD_ID, "coven");

    /**
     * @return {@code true} if the witch has completed the Ritual of Malice
     */
    boolean hasMalice();

    /**
     * @param hasMalice should be set to {@code true} when the witch completes the Ritual of Malice
     */
    void setMalice(boolean hasMalice);

    /**
     * Gets the list of Spell Components that the coven witch must successfully cast on a Witch mob
     * in order to progress to the next tier of magic.  If a tier returns an empty collection, the
     * witch has completed all the required effects for that tier.
     * @param tier is the tier of magic to check for {@code [3-5]}
     * @return a {@code Collection<SpellEffect>} of MnA Spell Components
     */
    Collection<SpellEffect> getTierEffectsRequired(int tier);

    /**
     * Sets the list of Spell Components that the coven witch must successfully cast on a Witch mob
     * in order to progress to the next tier of magic.
     * @param tier is the tier of magic to set {@code [3-5]}
     * @param effects is a {@code Set<SpellEffect>} of MnA Spell Components
     */
    void setTierEffectsRequired(int tier, Set<SpellEffect> effects);
}
