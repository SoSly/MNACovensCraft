package org.sosly.witchcraft.events.items;

import com.mna.items.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.blocks.BlockRegistry;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VinteumNeedle {
    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        LivingEntity attacker = event.getEntity();
        if (!(attacker instanceof Player player)) {
            return;
        }

        ItemStack heldItem = player.getItemInHand(player.getUsedItemHand());
        if (heldItem.getItem() != ItemInit.VINTEUM_NEEDLE.get()) {
            return;
        }

        Entity target = event.getTarget();
        CompoundTag tag = heldItem.getTag();
        if (tag != null && tag.getBoolean("bloody")) {
            return;
        }

        if (tag == null) {
            tag = new CompoundTag();
        }
        tag.putBoolean("bloody", true);
        if (target instanceof Player) {
            tag.putString("type", "player");
            tag.putUUID("uuid", target.getUUID());
        } else {
            tag.putString("type", ForgeRegistries.ENTITY_TYPES.getKey(target.getType()).toString());
            tag.putUUID("uuid", target.getUUID());
        }
        tag.putString("name", heldItem.getHoverName().getString());

        heldItem.setTag(tag);

        String itemName = heldItem.getHoverName().getString() + " (Bloody)";
        heldItem.setHoverName(Component.literal(itemName));

        if (target instanceof Mob mob) {
            mob.setPersistenceRequired();
        }
    }

    @SubscribeEvent
    public static void onCraftWithPoppet(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        Item PoppetItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Witchcraft.MOD_ID, "poppet"));
        Item BoundPoppetItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Witchcraft.MOD_ID, "bound_poppet"));
        ItemStack craftedItem = event.getCrafting();
        if (craftedItem.getItem() != BoundPoppetItem) {
            return;
        }

        ItemStack needle = ItemStack.EMPTY;
        int slot = -1;
        ItemStack poppet = ItemStack.EMPTY;
        for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
            ItemStack stack = event.getInventory().getItem(i);
            if (stack.getItem() == ItemInit.VINTEUM_NEEDLE.get()) {
                needle = stack;
                slot = i;
            } else if (stack.getItem() == PoppetItem) {
                poppet = stack;
            }
        }
        if (needle.isEmpty() || poppet.isEmpty()) {
            return;
        }

        CompoundTag tag = needle.getTag();
        if (tag == null || !tag.getBoolean("bloody")) {
            return;
        }

        UUID uuid = tag.getUUID("uuid");
        ServerLevel level = (ServerLevel) event.getEntity().level();
        Entity entity = level.getEntity(uuid);
        if (entity == null) {
            return;
        }

        String name = entity.getDisplayName().getString();
        if (!(entity instanceof Player)) {
            name = "a " + name;
        }

        CompoundTag poppetTag = craftedItem.getOrCreateTag();
        poppetTag.putUUID("bound_entity", uuid);
        craftedItem.setTag(poppetTag);
        craftedItem.setHoverName(Component.literal("Bound Poppet of " + name));

        cleanNeedle(needle);
        needle.setHoverName(Component.literal("Vinteum Needle"));

        // give the clean needle back to the player
        if (!event.getEntity().getInventory().add(needle)) {
            event.getEntity().drop(needle, false);
        }
    }

    @SubscribeEvent
    public static void onWash(PlayerInteractEvent event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(player.getUsedItemHand());
        if (heldItem.getItem() != ItemInit.VINTEUM_NEEDLE.get()) {
            return;
        }

        // check if the player is targeting water or a cauldron
        BlockHitResult hitResult = getPlayerPOVHitResult(player.level(), player);
        if (hitResult.getType() == BlockHitResult.Type.MISS) {
            return;
        }

        BlockPos pos = hitResult.getBlockPos();
        Direction direction = hitResult.getDirection();
        BlockPos offsetPos = pos.relative(direction);
        if (!player.mayUseItemAt(offsetPos, direction, heldItem)) {
            return;
        }
        BlockState blockState = player.level().getBlockState(pos);

        if (!blockState.getBlock().equals(Blocks.WATER) && !blockState.getBlock().equals(Blocks.WATER_CAULDRON)) {
            return;
        }

        CompoundTag tag = heldItem.getTag();
        if (tag == null || !tag.getBoolean("bloody")) {
            return;
        }

        String itemName = tag.getString("name");

        cleanNeedle(heldItem);

        player.level().playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.5F,
                2.6F + (player.level().random.nextFloat() - player.level().random.nextFloat()) * 0.8F);
    }

    private static BlockHitResult getPlayerPOVHitResult(Level level, Player player) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getBlockReach();
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.WATER, player));
    }

    private static void cleanNeedle(ItemStack needle) {
        CompoundTag tag = needle.getTag();
        if (tag == null || !tag.getBoolean("bloody")) {
            return;
        }

        tag.remove("bloody");
        tag.remove("type");
        tag.remove("uuid");
        tag.remove("name");

        if (tag.isEmpty()) {
            needle.setTag(null);
        }

        needle.setHoverName(Component.literal("Vinteum Needle"));
    }

    @Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void onItemColors(RegisterColorHandlersEvent.Item event) {
            event.getItemColors().register((stack, color) -> {
                CompoundTag tag = stack.getTag();
                if (tag == null || !tag.getBoolean("bloody")) {
                    return -1;
                }

                return 0xFF0000;
            }, ItemInit.VINTEUM_NEEDLE.get());
        }
    }
}
