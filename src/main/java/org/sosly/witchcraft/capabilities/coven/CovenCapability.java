package org.sosly.witchcraft.capabilities.coven;

import com.mna.api.spells.parts.SpellEffect;
import org.sosly.witchcraft.api.capabilities.ICovenCapability;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CovenCapability implements ICovenCapability {
    private boolean malice = false;
    private final Map<Integer, Set<SpellEffect>> tierEffectsRequired = new HashMap<>();

    @Override
    public boolean hasMalice() {
        return malice;
    }

    @Override
    public void setMalice(boolean hasMalice) {
        malice = hasMalice;
    }

    @Override
    public Collection<SpellEffect> getTierEffectsRequired(int tier) {
        return tierEffectsRequired.get(tier);
    }

    @Override
    public void setTierEffectsRequired(int tier, Set<SpellEffect> effects) {
        tierEffectsRequired.put(tier, effects);
    }
}
