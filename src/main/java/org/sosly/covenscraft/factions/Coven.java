package org.sosly.covenscraft.factions;

import com.mna.api.faction.BaseFaction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Coven extends BaseFaction {

    @Override
    public ItemStack getFactionGrimoire() {
        return null;
    }

    @Override
    public Item getTokenItem() {
        return null;
    }

    @Override
    public SoundEvent getRaidSound() {
        return null;
    }

    @Nullable
    @Override
    public SoundEvent getHornSound() {
        return null;
    }

    @Override
    public Component getOcculusTaskPrompt(int i) {
        return null;
    }

    @Override
    public ResourceLocation getFactionIcon() {
        return null;
    }

    @Nullable
    @Override
    public int[] getManaweaveRGB() {
        return new int[0];
    }

    @Override
    public ChatFormatting getTornJournalPageFactionColor() {
        return null;
    }
}
