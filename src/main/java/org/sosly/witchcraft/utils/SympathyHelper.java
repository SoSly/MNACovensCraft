package org.sosly.witchcraft.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SympathyHelper {
    public static boolean isBound(ItemStack item) {
        CompoundTag tag = item.getTag();
        return tag != null && tag.contains("target");
    }

    @Nullable
    public static Entity getBoundEntity(ItemStack item, ServerLevel level) {
        CompoundTag tag = item.getTag();
        if (tag == null || !tag.contains("target")) {
            return null;
        }

        UUID target = tag.getUUID("target");
        Entity entity = level.getPlayerByUUID(target);
        if (entity == null) {
            entity = level.getEntity(target);
        }
        return entity;
    }
}
