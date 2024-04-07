package org.sosly.witchcraft.capabilities.coven;

import com.mna.Registries;
import com.mna.api.spells.parts.SpellEffect;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sosly.witchcraft.api.capabilities.ICovenCapability;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CovenProvider implements ICapabilitySerializable<Tag> {
    public static final Capability<ICovenCapability> COVEN = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<ICovenCapability> holder = LazyOptional.of(CovenCapability::new);

    @Override
    public Tag serializeNBT() {
        ICovenCapability instance = holder.orElse(new CovenCapability());
        CompoundTag nbt = new CompoundTag();
        if (instance.hasMalice()) {
            nbt.putBoolean("malice", true);
        }
        for (int tier = 3; tier <= 5; tier++) {
            if (instance.getTierEffectsRequired(tier) != null) {
                CompoundTag tierTag = new CompoundTag();
                tierTag.putInt("size", instance.getTierEffectsRequired(tier).size());
                AtomicInteger index = new AtomicInteger(0);
                instance.getTierEffectsRequired(tier).forEach(effect -> {
                    tierTag.putString("effect_" + index.getAndIncrement(), effect.getRegistryName().toString());
                });
                nbt.put("tier_" + tier, tierTag);
            }
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        ICovenCapability instance = holder.orElse(new CovenCapability());
        if (nbt instanceof CompoundTag cnbt) {
            instance.setMalice(cnbt.getBoolean("malice"));
            for (int tier = 3; tier <= 5; tier++) {
                if (cnbt.contains("tier_" + tier)) {
                    CompoundTag tierTag = cnbt.getCompound("tier_" + tier);
                    Set<SpellEffect> effects = new HashSet<>();
                    for (int i = 0; i < tierTag.getInt("size"); i++) {
                        ResourceLocation effectName = new ResourceLocation(tierTag.getString("effect_" + i));
                        effects.add(Registries.SpellEffect.get().getValue(effectName));
                    }
                    instance.setTierEffectsRequired(tier, effects);
                }
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return COVEN.orEmpty(cap, holder);
    }
}
