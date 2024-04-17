package org.sosly.witchcraft.blocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.blocks.entities.BoundPoppetEntity;

public class EntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Witchcraft.MOD_ID);
    public static final RegistryObject<BlockEntityType<BoundPoppetEntity>> BOUND_POPPET = BLOCK_ENTITIES
            .register("bound_poppet", () -> BlockEntityType.Builder.of(BoundPoppetEntity::new,
                    BlockRegistry.BOUND_POPPET.get()).build(null));

}
