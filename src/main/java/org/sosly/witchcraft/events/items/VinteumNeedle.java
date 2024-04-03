package org.sosly.witchcraft.events.items;

import com.mna.items.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.sosly.witchcraft.Config;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.items.ItemRegistry;
import org.sosly.witchcraft.utils.SympathyHelper;

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
        if (!(target instanceof Player || target instanceof Mob)) {
            return;
        }

        if (Config.bossesImmuneToSympathy && SympathyHelper.isBoss(target)) {
            return;
        }

        CompoundTag tag = heldItem.getTag();

        ItemStack bloodyNeedle = new ItemStack(ItemRegistry.BLOODY_NEEDLE.get());
        if (tag == null) {
            tag = new CompoundTag();
        }
        tag.putUUID("target", target.getUUID());
        bloodyNeedle.setTag(tag);

        String name = bloodyNeedle.getDisplayName().getString() + " of ";

        if (target instanceof Mob mob) {
            mob.setPersistenceRequired();
            name += " a " + target.getDisplayName().getString();
        } else {
            name += target.getDisplayName().getString();
        }

        heldItem.setHoverName(Component.literal(name));
        player.setItemInHand(player.getUsedItemHand(), bloodyNeedle);
    }
}