package org.sosly.witchcraft.events.items;

import com.mna.api.events.RunicAnvilShouldActivateEvent;
import com.mna.blocks.BlockInit;
import com.mna.blocks.tileentities.RunicAnvilTile;
import com.mna.items.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.items.ItemRegistry;
import org.sosly.witchcraft.items.alchemy.PotionPouchItem;

import java.util.List;

@Mod.EventBusSubscriber(modid = Witchcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PotionPouch {
    @SubscribeEvent
    public static void onRunicAnvil(RunicAnvilShouldActivateEvent event) {
        ItemStack stack = event.pattern;
        ItemStack stack1 = event.material;
        if (stack.getItem() == ItemRegistry.POTION_POUCH.get()) {
            if (stack1.getItem() == ItemInit.PATCH_DEPTH.get() || stack1.getItem() == ItemInit.PATCH_DEPTH_2.get()) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public static void onUseSewingKit(PlayerInteractEvent event) {
        Level level = event.getLevel();
        if (level.isClientSide()) {
            return;
        }

        ItemStack itemInHand = event.getItemStack();
        if (itemInHand.getItem() != ItemInit.SORCEROUS_SEWING_SET.get()) {
            return;
        }

        BlockPos pos = event.getPos();
        Block block = level.getBlockState(pos).getBlock();
        if (block != BlockInit.RUNIC_ANVIL.get()) {
            return;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof RunicAnvilTile)) {
            return;
        }

        RunicAnvilTile runicAnvilTile = (RunicAnvilTile) blockEntity;
        ItemStack baseItem = runicAnvilTile.getItem(0);
        if (baseItem.getItem() != ItemRegistry.POTION_POUCH.get()) {
            return;
        }

        ItemStack material = runicAnvilTile.getItem(1);
        if (!((PotionPouchItem)baseItem.getItem()).addPatchToPouch(baseItem, material)) {
            return;
        }

        runicAnvilTile.setItem(0, baseItem);
        runicAnvilTile.setItem(1, ItemStack.EMPTY);
        itemInHand.setDamageValue(itemInHand.getDamageValue() + 1);
        event.setCanceled(true);
    }
}
