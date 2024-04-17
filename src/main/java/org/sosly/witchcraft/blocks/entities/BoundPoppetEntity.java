package org.sosly.witchcraft.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.sosly.witchcraft.blocks.EntityRegistry;

import java.util.UUID;

public class BoundPoppetEntity extends BlockEntity {
    UUID target;
    String type;

    public BoundPoppetEntity(BlockPos pos, BlockState state) {
        super(EntityRegistry.BOUND_POPPET.get(), pos, state);
    }

    public UUID target() {
        return target;
    }

    public String type() {
        return type;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putUUID("target", target);
        tag.putString("type", type);
        super.saveAdditional(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        target = tag.getUUID("target");
        type = tag.getString("type");
        super.load(tag);
    }
}
