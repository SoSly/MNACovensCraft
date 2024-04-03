package org.sosly.witchcraft.utils;

import com.mna.entities.boss.BossMonster;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SympathyHelper {
    public static boolean isBound(ItemStack item) {
        CompoundTag tag = item.getTag();
        return tag != null && tag.contains("target");
    }

    public static boolean isBoss(Entity entity) {
        return (entity instanceof WitherBoss) || (entity instanceof EnderDragon) || (entity instanceof BossMonster);
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
